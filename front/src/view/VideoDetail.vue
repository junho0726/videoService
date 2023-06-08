<template>
  <div>
      <Header @show-sidebar="showSidebar()"/>
      <div class="content-wrap">
          <SideBar v-if="isShowSidebar"/>
          <div class="content" :class="{ 'show-side-bar-content' : isShowSidebar }">
              <div class="video-content">
                  <video  v-if="video.fileFullPath != null"
                          id="video"
                          class="video-js"
                          controls
                          autoplay
                          preload="auto"
                          :poster="video.thumbnailFullPath">
                      <source :src="video.fileFullPath" type="video/mp4">
                  </video>
                  <h2>{{ video.videoTitle }}</h2>
                  <div class="channel-info-box">
                      <div class="channel-info-row">
                          <img class="profile-img" src="/basic_profile.png">
                          <div class="video-info-div">
                              <span class="channel-name">{{ video.channelName }}</span>
                              <span>구독자 {{ video.subscribeCount }}명</span>
                          </div>
                          <div v-if="!isMyVideo" class="subscribe-div">
                              <button v-if="!isSubscribe" class="btn-subscribe" @click="subscribe()">구독</button>
                              <button v-if="isSubscribe" class="btn-subscribe" @click="subscribe()">구독 중</button>
                          </div>
                      </div>
                      <div class="feedback-box">
                        <div class="feedback-div" :class="{ 'show-side-bar-feedback-div' : isShowSidebar }">
                            <img :src="isActionGood === true ? '/action_good.png' : '/good.png'" @click="feedback('Y')">
                            <span>{{ video.likeStateCount }}</span>
                            <div class="line"></div>
                            <img :src="isActionBad === true ? '/action_bad.png' : '/bad.png'" @click="feedback('N')">
                        </div>
                        <div class="more-feedback-div" :class="{ 'show-side-bar-more-feedback-div' : isShowSidebar }" @mouseover="showMoreFeedbackList()" @mouseleave="closeMoreFeedbackList()">
                          <img class="more-feedback-img" src="/more_feedback.png">
                          <div class="more-feedback-list" v-if="isShowMoreFeedbackList">
                            <span @click="handleSaveVideoState('Y')" v-if="video.saveVideoState == 'N' || video.saveVideoState == null">저장</span>
                            <span @click="handleSaveVideoState('N')" v-if="video.saveVideoState == 'Y'">저장됨</span>
                            <span @click="sendFeedback('신고')">신고</span>
                            <span @click="sendFeedback('뭐하지')">뭐하지</span>
                          </div>
                        </div>
                      </div>
                  </div>
                  <div class="video-detail-info" :class="{ 'show-side-bar-video-detail-info' : isShowSidebar }">
                      <h4>조회수 {{ video.videoCount }}회</h4>
                      <span>{{ video.videoContent}}</span>
                  </div>
                  <br>
                  <div class="comment-write-div">
                      <img class="profile-img" src="/basic_profile.png">
                      <input v-model="comment" class="comment-input" type="text">
                      <button class="btn-save-comment" :class="{ 'show-side-bar-btn-save-comment' : isShowSidebar }" @click="saveComment()">작성</button>
                  </div>
                  <div class="comment-list" v-for="(comment, index) in commentList">
                      <div class="comment-line">
                          <div class="comment-info-box">
                              <img class="profile-img" src="/basic_profile.png">
                              <div class="comment-col">
                                  <span>{{ comment.userName }}</span>
                                  <span>{{ comment.content }}</span>
                              </div>
                          </div>
                          <div class="more-comment">
                              <span v-if="writeCommentMoreIndex != index" @click="writeCommentMore(index)">더 보기</span>
                              <span v-if="writeCommentMoreIndex == index" @click="writeCommentMore(index)">취소</span>
                          </div>
                      </div>
                      <div class="comment-list" v-for="moreComment in comment.children" v-show="writeCommentMoreIndex == index">
                          <div class="more-comment-line">
                              <div class="more-comment-info-box">
                                  <img class="more-profile-img" src="/basic_profile.png">
                                  <div class="more-comment-col">
                                      <span>{{ moreComment.userName }}</span>
                                      <span>{{ moreComment.content }}</span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="comment-write-div" v-if="writeCommentMoreIndex == index">
                          <img class="more-profile-img" src="/basic_profile.png">
                          <input v-model="moreComment" class="comment-input" type="text">
                          <button class="btn-save-comment" :class="{ 'show-side-bar-btn-save-comment' : isShowSidebar }" @click="saveComment(comment.commentSeq)">작성</button>
                      </div>
                  </div>
              </div>
              <div>
                TODO
              </div>
          </div>
      </div>
  </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import SideBar from "@/layout/SideBar.vue"
import {ref} from "vue";
import router from "@/router";
import instance from "@/api/axios";
import axios from "axios";
let props = defineProps({
    seq: String
})

let comment = ref('');
let moreComment = ref('');
let video = ref({});
let commentList = ref([]);
let isShowSidebar = ref(false);
let isActionGood = ref(false);
let isActionBad = ref(false);
let isShowMoreFeedbackList = ref(false);
let isMyVideo = ref(false);
let isSubscribe = ref(false);
let writeCommentMoreIndex = ref(-1);

instance.get('/api/video/findDetail/' + props.seq).then(value => {
    console.log(value.data.data)
    if(value.data.code === '0000') {
      video.value = value.data.data;
      if(video.value.channelSeq == localStorage.getItem('channelSeq')) {
          isMyVideo.value = true;
      } else {
          if(video.value.subscribeState == 'Y') {
              isSubscribe.value = true
          } else {
              isSubscribe.value = false
          }
      }
      handleLikeState(video.value.likeState);
      getCommentList();
    } else {
        alert('예상치 못한 오류 발생');
    }
}).catch(reason => {
    console.log(reason);
    alert('예상치 못한 오류 발생');
});

function subscribe() {
    if(localStorage.getItem('token') === null) {
        alert('로그인 후 이용해주세요.');
        router.push('/login');
    } else {
        instance.post('/api/subscribe/save', {
            'channel': {'channelSeq': video.value.channelSeq},
            'user': {'userSeq': localStorage.getItem('seq')}
        }).then(value => {
            console.log(value)
            if (value.data.code === '0000') {
                let result = value.data.data;
                if (result.subscribeState) {
                    isSubscribe.value = true
                } else {
                    isSubscribe.value = false
                }
                video.value.subscribeCount = result.subscribeCount;
            }
        }).catch(reason => {
            console.log(reason);
        })
    }
}

function saveComment(commentSeq) {
    if(localStorage.getItem('token') === null) {
        alert('로그인 후 이용해주세요.');
        router.push('/login');
    } else {
        if(commentSeq == null) {
            instance.post('/api/comment/save', {
                'videoSeq': video.value.videoSeq,
                'content': comment.value
            }).then(value => {
                if(value.data.code === '0000') {
                    alert('댓글이 정상적으로 작성되었습니다.');
                    getCommentList();
                    comment.value = '';
                    moreComment.value = '';
                }
            }).catch(reason => {
                console.log(reason);
            })
        } else {
            instance.post('/api/comment/save', {
                'videoSeq': video.value.videoSeq,
                'content': moreComment.value,
                'parentSeq': commentSeq
            }).then(value => {
                if(value.data.code === '0000') {
                    alert('댓글이 정상적으로 작성되었습니다.');
                    getCommentList();
                    comment.value = '';
                }
            }).catch(reason => {
                console.log(reason);
            })
        }
    }
}

function feedback(state) {
    if(localStorage.getItem('token') === null) {
        alert('로그인 후 이용해주세요.');
        router.push('/login');
    } else {
        instance.post('/api/like/likeStateInsert', {
          userSeq : localStorage.getItem('seq'),
          likeState : state,
          videoSeq : video.value.videoSeq
        }).then(value => {
            console.log(value.data.data)
          if(value.data.code === '0000') {
            video.value.likeStateCount = value.data.data.likeCount;
            handleLikeState(value.data.data.likeState);
          } else {
            alert('예상치 못한 오류 발생');
          }
        }).catch(reason => {
          alert('예상치 못한 오류 발생');
          console.log(reason);
        })
    }
}

function getCommentList() {
    axios.get('/api/comment/parent/list?videoSeq=' + video.value.videoSeq
    ).then(value => {
        if(value.data.code === '0000') {
            commentList.value = [];
            let result = value.data.data;
            for (let i = 0; i < result.length; i++) {
                commentList.value.push(result[i])
            }
            console.log(commentList.value)
        }
    }).catch(reason => {
        console.log(reason)
    })
}

function writeCommentMore(index) {
    if(writeCommentMoreIndex.value == index) {
        writeCommentMoreIndex.value = -1
    } else {
        writeCommentMoreIndex.value = index
    }
}

function showMoreFeedbackList() {
  isShowMoreFeedbackList.value = true;
}

function closeMoreFeedbackList() {
  isShowMoreFeedbackList.value = false;
}

function sendFeedback() {
}

function handleSaveVideoState(state) {
    if (video.value.saveVideoState != null) {
        instance.post('/api/saveVideo/handleState', {
            'saveVideoSeq':video.value.saveVideoStateSeq,
            'state':state,
            'videoSeq':video.value.videoSeq
        }).then(value => {
            if (value.data.code == '0000') {
                let result = value.data.data;
                video.value.saveVideoState = result.state;
                video.value.saveVideoStateSeq = result.saveVideoSeq;
            } else {
                console.log('실패')
                console.log(value)
            }
        }).catch(reason => {
            console.log(reason);
        })
    } else {
        alert('로그인 후 이용해주세요.');
        location.href = '/login';
    }
}

function handleLikeState(state) {
    if(state === 'Y') {
        isActionGood.value = true;
        isActionBad.value = false;
    } else if(state === 'N') {
        isActionBad.value = true;
        isActionGood.value = false;
    } else {
        isActionBad.value = false;
        isActionGood.value = false;
    }
}

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style scoped>

h4 {
    margin: 0 0;
}

.content-wrap {
  width: 100%;
}

.content {
    width: 100%;
    display: flex;
    padding: 3%;
}

.show-side-bar-content {
    width: 100%;
    display: flex;
    background-color: rgba(0, 0, 0, 0.5) !important;
    padding: 3%;
}

.video-content {
    width: 75%;
    display: flex;
    flex-direction: column;
}

#video {
    width: 100%;
    height: 650px;
}

.channel-info-box {
    width: 100%;
    display: flex;
    justify-content: space-between;
}

.channel-info-row {
    width: 40%;
    display: flex;
}

.video-info-div {
    height: 100%;
    display: flex;
    flex-direction: column;
    text-align: left;
    justify-content: center;
    margin: 0 1.5%;
}

.video-info-div span {
    margin: 1% 0;
    font-size: 13px;
}

.channel-name {
    font-size: 15px !important;
    font-weight: bold;
}

.subscribe-div {
    display: flex;
    flex-direction: column;
    align-content: end;
    text-align: right;
    padding-top: 11px;
}

.feedback-box {
  width: 26%;
  display: flex;
}

.feedback-div {
    display: flex;
    width: 100%;
    background-color: #F9F9F9;
    border-radius: 40px;
    justify-content: left;
    align-items: center;
}

.show-side-bar-feedback-div {
    display: flex;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 40px;
    justify-content: left;
    align-items: center;
}

.feedback-div span {
    margin: 0 4%;
    font-size: 20px;
    font-weight: bold;
}

.line {
    height: 80%;
    margin-top: 1%;
    border-left: solid 1px;
}

.feedback-div img {
    margin-left: 10%;
    width: 40px;
    height: 40px;
}

.more-feedback-div {
  width: 20%;
  padding: 0 10%;
  display: flex;
  margin: 0 10%;
  background-color: #F9F9F9;
  border-radius: 40px;
  border: none;
  align-items: center;
  justify-content: center;
  position: relative;
}

.show-side-bar-more-feedback-div {
    width: 20%;
    padding: 0 10%;
    display: flex;
    margin: 0 10%;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 40px;
    border: none;
    align-items: center;
    justify-content: center;
    position: relative;
}

.more-feedback-img {
  height: 10px !important;
  width: 50px !important;
}

.more-feedback-list {
  position: absolute;
  z-index: 999;
  background-color: #F2F2F2;
  border-color: #F2F2F2;
  border-radius: 50px;
  width: 100px;
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30% 0;
  bottom: 100%;
}

.more-feedback-list span {
    margin-bottom: 10%;
    font-size: 20px !important;
    font-weight: bold;
}

.video-detail-info {
    margin: 1% 0;
    width: 100%;
    height: auto;
    background-color: #F2F2F2;
    border: none;
    border-radius: 15px;
    padding: 2% 0;
    display: flex;
    flex-direction: column;
}

.show-side-bar-video-detail-info {
    margin: 1% 0;
    width: 100%;
    height: auto;
    background-color: rgba(0, 0, 0, 0.5);
    border: none;
    border-radius: 15px;
    padding: 2% 0;
    display: flex;
    flex-direction: column;
}

.video-detail-info * {
    margin: 0 2%;
}

.video-detail-info span {
    margin-top: 1%;
}

.comment-write-div {
    margin: 1% 7.5px 0;
    display: flex;
}

.comment-input {
    width: 95%;
    margin: 0 1%;
    border: none;
    border-bottom: solid 1px #F2F2F2;
    outline: none;
    font-weight: bold;
}

.btn-save-comment {
    border: none;
    background-color: #F2F2F2;
    border-radius: 15px;
    width: 5%;
    font-size: 17px;
    font-weight: bold;
}

.show-side-bar-btn-save-comment {
    border: none;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 15px;
    width: 5%;
    font-size: 17px;
    font-weight: bold;
}

.comment-list {
    display: flex;
    flex-direction: column;
}

.comment-line {
    display: flex;
    justify-content: space-between;
    margin: 2% 0 0;
}

.more-comment-line {
    display: flex;
    margin: 1% 7.5px 0;
}

.comment-info-box {
    display: flex;
    width: 93%;
}

.more-comment-info-box {
    display: flex;
    width: 93%;
}

.comment-col {
    margin: 0 1%;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: center;
    font-size: 17px;
}

.more-comment-col {
    margin: 0 1%;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: center;
    font-size: 14px;
}

.more-comment {
    display: flex;
    align-items: center;
    width: max-content;
    margin-right: 1%;
    margin-bottom: 5px;
}

.more-profile-img {
    width: 40px;
    height: 40px;
}
</style>
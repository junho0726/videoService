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
                            <span @click="sendFeedback('저장')">저장</span>
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
                  <div class="commend-write-div">
                      <img class="profile-img" src="/basic_profile.png">
                      <input v-model="commend" class="commend-input" type="text">
                      <button class="btn-save-commend" :class="{ 'show-side-bar-btn-save-commend' : isShowSidebar }" @click="saveCommend()">작성</button>
                  </div>
                  <div class="commend-list">
                      <div class="commend-line">
                          <img class="profile-img" src="/basic_profile.png">
                          <div class="commend-col">
                              <span>경민상</span>
                              <span>ㅋㅋㅋㅋㅋㅋㅋㅋ개웃기노</span>
                          </div>
                      </div>
                      <div class="commend-line">
                          <img class="profile-img" src="/basic_profile.png">
                          <div class="commend-col">
                              <span>슈퍼맨</span>
                              <span>이게 웃기세요??</span>
                          </div>
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

let commend = ref('');
let video = ref({});
let isShowSidebar = ref(false);
let isActionGood = ref(false);
let isActionBad = ref(false);
let isShowMoreFeedbackList = ref(false);
let isMyVideo = ref(false);
let isSubscribe = ref(false);

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
      axios.get('/api/commend/list?videoSeq=' + video.value.videoSeq
          ).then(value => {
            console.log(value);
          }).catch(reason => {
            console.log(reason)
          })
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

function saveCommend() {
    if(localStorage.getItem('token') === null) {
        alert('로그인 후 이용해주세요.');
        router.push('/login');
    } else {
        instance.post('/api/commned/save', {
            'videoSeq': video.value.videoSeq,
            'commend': commend.value
        }).then(value => {
            console.log(value);
        }).catch(reason => {
            console.log(reason);
        })
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

function showMoreFeedbackList() {
  isShowMoreFeedbackList.value = true;
}

function closeMoreFeedbackList() {
  isShowMoreFeedbackList.value = false;
}

function sendFeedback(what) {
  alert(what);
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

.btn-subscribe {
    border: none;
    border-radius: 40px;
    background-color: black;
    color: white;
    font-size: 16px;
    width: 80px;
    height: 40px;
    margin: 0 2%;
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
  justify-content: left;
  align-items: center;
  padding: 30% 0;
  bottom: 100%;
  font-size: 20px;
  font-weight: bold;
}

.more-feedback-list span {
  margin-bottom: 10%;
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

.commend-write-div {
    display: flex;
}

.commend-input {
    width: 95%;
    margin: 0 1%;
    border: none;
    border-bottom: solid 1px #F2F2F2;
    outline: none;
    font-weight: bold;
}

.btn-save-commend {
    border: none;
    background-color: #F2F2F2;
    border-radius: 15px;
    width: 5%;
    font-size: 17px;
    font-weight: bold;
}

.show-side-bar-btn-save-commend {
    border: none;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 15px;
    width: 5%;
    font-size: 17px;
    font-weight: bold;
}

.commend-list {
    display: flex;
    flex-direction: column;
}

.commend-line {
    display: flex;
    margin: 2% 0 0;
}

.commend-col {
    margin: 0 1%;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: center;
    font-size: 17px;
}
</style>
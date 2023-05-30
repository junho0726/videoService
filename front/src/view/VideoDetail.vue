<template>
  <div>
      <Header/>
      <div class="content-wrap">
          <SideBar v-if="isShowSidebar"/>
          <div class="content">
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
                              <span>구독자 0명</span>
                          </div>
                          <div class="subscribe-div">
                              <button class="btn-subscribe">구독</button>
                          </div>
                      </div>
                      <div class="feedback-box">
                        <div class="feedback-div">
                            <img :src="isActionGood === true ? '/action_good.png' : '/good.png'" @click="feedback('Y')">
                            <span>{{ video.likeStateCount }}</span>
                            <div class="line"></div>
                            <img :src="isActionBad === true ? '/action_bad.png' : '/bad.png'" @click="feedback('N')">
                        </div>
                        <div class="more-feedback-div" @mouseover="showMoreFeedbackList()" @mouseleave="closeMoreFeedbackList()">
                          <img class="more-feedback-img" src="/more_feedback.png">
                          <div class="more-feedback-list" v-if="isShowMoreFeedbackList">
                            <span @click="sendFeedback('저장')">저장</span>
                            <span @click="sendFeedback('신고')">신고</span>
                            <span @click="sendFeedback('뭐하지')">뭐하지</span>
                          </div>
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
import store from "@/store";
import router from "@/router";
import instance from "@/api/axios";
let props = defineProps({
    seq: String
})

let video = ref({});
let isActionGood = ref(false);
let isActionBad = ref(false);
let isShowMoreFeedbackList = ref(false);

instance.get('/api/video/findDetail/' + props.seq).then(value => {
    if(value.data.code === '0000') {
      video.value = value.data.data;
      if(video.value.likeState === 'Y') {
        isActionGood.value = true;
        isActionBad.value = false;
      } else if(video.value.likeState === 'N') {
        isActionBad.value = true;
        isActionGood.value = false;
      } else {
        isActionBad.value = false;
        isActionGood.value = false;
      }
    } else {
        alert('예상치 못한 오류 발생');
    }
}).catch(reason => {
    console.log(reason);
    alert('예상치 못한 오류 발생');
});

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
          if(value.data.code === '0000') {
            let result = value.data.data.likeState;
            video.value.likeStateCount = value.data.data.likeCount;
            if(result.likeState === 'Y') {
              isActionGood.value = true;
              isActionBad.value = false;
            } else if(result.likeState === 'N') {
              isActionBad.value = true;
              isActionGood.value = false;
            } else {
              isActionBad.value = false;
              isActionGood.value = false;
            }
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

</script>

<style scoped>
.content-wrap {
  width: 100%;
}

.content {
  width: 100%;
  display: flex;
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

</style>
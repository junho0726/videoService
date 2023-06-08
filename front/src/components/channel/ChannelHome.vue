<template>
    <div class="contents">
        <div class="div-none-video" v-if="isEmptyVideo">
            <img src="/none_video.png">
            <h2>동영상을 업로드하여 시작하기</h2>
            <span>스토리를 공유하고 시청자와 소통해 보세요. 업로드한 동영상이 여기에 표시됩니다.</span>
            <div>
                <br>
                <button class="btn-upload" @click="handelModal()">동영상 업로드</button>
            </div>
            <div>
                <br>
                <span><a href="https://www.youtube.com/creators/how-things-work/getting-started/?hl=ko" target="_blank">시작하는 방법 </a>알아보기</span>
            </div>
        </div>
        <div class="uploaded-video-div" v-if="!isEmptyVideo">
          <span class="span-upload" @click="handelModal()">동영상 업로드</span>
          <div class="uploaded-video-box">
            <h4>업로드된 동영상</h4>
              <div class="line"></div>
              <br>
            <div class="video-list">
              <div class="video-box" v-for="(myVideo, index) in myVideoList">
                  <div class="video-div">
                    <img :src="myVideo.thumbnailFullPath" @click="showVideoDetail(myVideo.videoSeq)">
                    <div class="video-info">
                      <span @click="showVideoDetail(myVideo.videoSeq)">{{ myVideo.videoTitle }}</span>
                      <span>조회수 {{ myVideo.videoCount }}</span>
                      <span>좋아요 {{ myVideo.likeStateCount }}</span>
                    </div>
                  </div>
                  <div class="video-menu" @mouseover="showMoreMenu(index)" @mouseleave="closeMoreMenu(index)">
                      <img src="/more_feedback.png">
                      <div class="more-menu" v-if="isShowMoreMenu[index]" @mouseover="showMoreMenu(index)" @mouseleave="closeMoreMenu(index)">
                          <span @click="updateVideo(index)">수정</span>
                          <div class="line"></div>
                          <span @click="deleteVideo(myVideo.videoSeq)">삭제</span>
                      </div>
                      <UpdateVideoModal @closeModal="handelUpdateModal(index)" :video-seq="myVideo.videoSeq" :show-modal="showUpdateModal[index]"/>
                  </div>
              </div>
            </div>
          </div>
        </div>
    </div>
    <UploadVideoModal @closeModal="handelModal()" :show-modal="showModal" />

</template>

<script setup>
import {ref} from "vue";
import UploadVideoModal from "@/modal/UploadVideoModal.vue";
import UpdateVideoModal from "@/modal/UpdateVideoModal.vue";
import instance from "@/api/axios";
import router from "@/router";

let showModal = ref(false);
let isEmptyVideo = ref(true);
let myVideoList = ref([]);
let isShowMoreMenu = ref([]);
let showUpdateModal = ref([]);

instance.get('/api/video/findAll?channelSeq=' + localStorage.getItem('channelSeq')).then(value => {
  let result = value.data;
  if(result.code === '0000') {
      if(result.data.length === 0) {
        isEmptyVideo.value = true;
      } else {
        for(let i = 0; i < result.data.length; i++) {
          myVideoList.value.push(result.data[i]);
          isShowMoreMenu.value[i] = false
          showUpdateModal.value[i] = false
        }
        isEmptyVideo.value = false;
      }
  } else {
    alert('개발 중입니다. 서버를 재실행 해주세요.');
  }
}).catch(reason => {
  console.log(reason);
  alert('개발 중입니다. 서버를 재실행 해주세요.');
})

function handelModal() {
    showModal.value = !showModal.value;
}

function handelUpdateModal(index) {
    showUpdateModal.value[index] = false;
}

function showVideoDetail(videoSeq) {
    router.push({ name: 'videoDetail', params: { seq: videoSeq }})
}

function showMoreMenu(index) {
    isShowMoreMenu.value[index] = true
}

function closeMoreMenu(index) {
    isShowMoreMenu.value[index] = false
}

function deleteVideo(videoSeq) {
    if (confirm('정말 삭제하시겠습니까?')) {
      instance.get('/api/video/delete/' + videoSeq
      ).then(value => {
          if (value.data.code == '0000') {
              alert('삭제되었습니다.');
              location.reload();
          } else {
              alert(value.data.message);
          }
      }).catch(reason => {
          console.log(reason);
          alert('예상치 못한 오류 발생');
      })
    }
}

function updateVideo(index) {
    showUpdateModal.value[index] = true;
}

</script>

<style scoped>

.video-list {
    display: flex;
    flex-direction: column;
}

.video-box {
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: auto !important;
    margin: 1%;
}

.video-div {
    display: flex;
    width: 80%;
    margin: 1%;
}

.video-info {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 30% !important;
    margin: 0 3%;
}

.video-info span {
    align-self: start;
}

.div-none-video {
    margin-top: 5%;
}

img {
    width: 140px;
    height: 130px;
}

.btn-upload {
    background-color: #3EA6FF;
    font-size: 15px;
    width: 120px;
    height: 35px;
    border: none;
    border-radius: 20px;
}

h2 {
    margin-bottom: 5px;
}

h4 {
  color: slategrey;
  align-self: start;
}

.uploaded-video-div {
  display: flex;
  flex-direction: column;
}

.uploaded-video-box {
  display: flex;
  flex-direction: column;
}

.span-upload {
  display: flex;
  align-self: end;
  align-items: center;
  justify-content: center;
  background-color: #3EA6FF;
  font-size: 15px;
  width: 120px;
  height: 35px;
  border: none;
  border-radius: 20px;
  margin: 1%;
}

.video-menu {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 10%;
}

.video-menu img {
    width: 50px;
    height: 10px;
}

.more-menu {
    display: flex;
    flex-direction: column;
    position: absolute;
    border:  solid 1px #F2F2F2;
    border-radius: 15px;
    padding: 8% 12%;
    bottom: 60%;
    font-weight: bold;
    font-size: 17px;
}
</style>
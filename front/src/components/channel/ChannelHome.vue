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
            <div v-for="myVideo in myVideoList">
              {{ myVideo }}
            </div>
          </div>
        </div>
    </div>
    <UploadVideoModal @closeModal="handelModal()" :show-modal="showModal" />

</template>

<script setup>
import {ref} from "vue";
import UploadVideoModal from "@/modal/UploadVideoModal.vue";
import instance from "@/api/axios";

let showModal = ref(false);
let isEmptyVideo = ref(true);
let myVideoList = ref([]);

instance.get('/api/video/findMyVideoAll').then(value => {
  let result = value.data;
  if(result.code === '0000') {
      console.log(result);
      if(result.data.length === 0) {
        isEmptyVideo.value = true;
      } else {
        for(let i = 0; i < result.data.length; i++) {
          myVideoList.value.push(result.data[i]);
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


</script>

<style scoped>

.contents {
    text-align: center;
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

a {
    color: #3EA6FF;
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

</style>
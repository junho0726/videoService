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
        <div v-if="!isEmptyVideo">
          동영상아 나와라 ~~
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

instance.get('/api/video/findMyVideoAll').then(value => {
  let result = value.data;
  if(result.code === '0000') {
      console.log(result);
    isEmptyVideo.value = false;
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

</style>
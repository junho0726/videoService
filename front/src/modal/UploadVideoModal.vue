<template>
    <div class="overlay" v-show="showModal"></div>

    <!--  파일 올리기 전  -->
    <div v-if="!isUploadFile" @drop="handleFile($event)" @dragover.prevent class="videoModal" v-show="showModal">
        <div class="modal-title">
            <span>동영상 업로드</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-contents">
            <button class="btn-upload"><img src="@/assets/upload_video.png"></button>
            <span>동영상 파일을 드래그 앤 드롭하여 업로드</span>
            <input type="file" ref="inputFile" @change="handleFile" style="display: none;">
            <button class="btn-select"  @click="openFile()">파일 선택</button>
        </div>
    </div>

    <!--  파일 올린 후  -->
    <div v-if="isUploadFile" class="videoModal" v-show="showModal">
        <div class="modal-title">
            <span>{{ fileInfo.title }}</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-file-info-box">
            <div class="modal-file-info">
                <br>
                <div class="file-title">
                    <p>제목(필수 항목)</p>
                    <textarea type="text" v-model="fileInfo.title"/>
                </div>
                <br>
                <div class="file-contents">
                    <p>설명</p>
                    <textarea type="text" v-model="fileInfo.contents" placeholder="시청자에게 동영상에 대해 이야기하기(채널을 멘션하려면 @ 입력)"/>
                </div>
                <div class="thumbnail-box">
                    <h5>미리보기 이미지</h5>
                    <div>
                      <span>지금은 Shorts 동영상의 썸네일을 변경할 수 없습니다.</span>
                    </div>
                </div>
                <div class="video-list-box">
                  <h5>재생목록 </h5>
                  <div>
                    <span>동영상을 1개 이상의 재생목록에 추가하세요. 시청자가 내 콘텐츠를 더 빨리 발견하는 데 도움이 될 수 있습니다.</span>
                  </div>
                  <button class="select-list">
                    선택
                  </button>
                </div>
            </div>
            <div class="videoBox">
                <video
                  id="video-box"
                  class="video-js"
                  controls
                  autoplay
                  preload="auto"
                  poster="@/assets/my_logo.png"
                  data-setup="{}">
                  <source :src="fileInfo.link" type="video/mp4"/>
                </video>
                <div class="video-info">
                  <div>
                    <p>동영상 링크</p>
                    <div class="link-row">
                      <div class="video-link">
                        <a :href="fileInfo.link" target="_blank" ref="linkForCopy">{{ fileInfo.link }}</a>
                      </div>
                      <div class="copy-video-link">
                        <span @click="copyLink()">Link</span>
                      </div>
                    </div>
                  </div>
                  <br>
                  <div>
                    <p>파일 이름</p>
                    <span>{{ fileInfo.name }}</span>
                  </div>
                    <div class="div-btn">
                        <button class="btn-cancel" @click="$emit('closeModal')">취소</button>
                        <button class="btn-post" @click="post()">게시</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import store from "@/store";

defineProps({
    showModal: Boolean
});

let inputFile = ref();
let linkForCopy = ref();
let correctFile = ref(false);
let isUploadFile = ref(false);
let fileInfo = ref({
  name: '',
  title: '',
  contents: '',
  link: 'http://localhost:8080/video/KakaoTalk_20230518_172717609.mp4'
});

function handleFile(event) {
    event.preventDefault();
    let file = null;
    if(inputFile.value == null) {
        file = event.dataTransfer.files[0];
        uploadFile(file);
    } else {
        file = inputFile.value.files[0];
        uploadFile(file);
    }
}

async function uploadFile(file) {
  // eslint-disable-next-line no-unused-vars
  await axios.post('/api/video/insertProc', {
      videoFile: file
  }, {
      headers: {
          'Content-Type': 'multipart/form-data',
          "Access_Token": store.getters['user/getToken']
      }
  }).then(value => {
      // if(value.data.code == "0000") {
        console.log("??")
        value.data;
        console.log(value.data + "!@#");
        console.log("??")
        setFileInfo(value.data);
      // }
  }).catch(reason => {
      console.log(reason);
  });
}

function setFileInfo(responseFile) {
    if(checkType(responseFile)) {
        // fileInfo.value.name = response.name;
        // fileInfo.value.title = response.name.substring(0, file.name.lastIndexOf('.'));
        // fileInfo.value.link = response.link;
        isUploadFile.value = true;
    } else {
        alert('확장자를 확인하거나 재시도 해주세요.');
    }
}

function openFile() {
    inputFile.value.click();
}

function checkType(file) {
    if(file.type == "video/mp4") {
        correctFile.value = true;
        return true;
    }
    return false;
}

function copyLink() {
    let copyLink = document.createElement('input');
    copyLink.value = fileInfo.value.link;
    document.body.append(copyLink);
    copyLink.select();
    document.execCommand('copy');
    document.body.removeChild(copyLink);
    alert('복사 되었습니다.');
}

function post() {
    if(fileInfo.value.title.trim() == "") {
        alert("제목은 필수 항목란입니다.");
    } else {

    }
}



</script>

<style scoped>

a {
  display: block;
  color: #3EA6FF !important;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

h5 {
  margin-bottom: 8px;
}

span {
  font-size: 12px;
  color: slategrey;
}

.overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
}

.videoModal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    z-index: 10000;
    width: 60%;
    height: 90%;
    display: flex;
    flex-direction: column;
}

.modal-title {
    display: flex;
    justify-content: space-between;
    border-bottom: solid 1px black;
    padding-bottom: 20px;
}

.modal-title span {
    font-size: 22px;
    font-weight: bold;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.modal-title button {
    border: none;
    background-color: white;
    color: black;
    font-size: 22px;
}

.modal-contents {
    text-align: center;
    margin-top: 15%;
}

.modal-contents span {
  display: block;
  margin: 3% 0;
}

.btn-upload {
    border: none;
    border-radius: 50%;
    width: 180px;
    height: 180px;
    background-color: slategrey;
}

.btn-select {
    border: none;
    border-radius: 5%;
    background-color: #3EA6FF;
    width: 120px;
    height: 40px;
}

.modal-file-info-box {
    display: flex;
}

.modal-file-info {
    display: flex;
    flex-direction: column;
    width: 70%;
}

.file-title {
    border: solid 1px black;
    border-radius: 5px;
    width: 100%;
    height: 70px;
    overflow: hidden;
}

.file-contents {
    border: solid 1px black;
    border-radius: 5px;
    width: 100%;
    height: 170px;
    overflow: hidden;
}

.modal-file-info-box p {
    margin: 3px 4px;
    width: 95%;
    color: slategrey;
}

.modal-file-info-box textarea {
    width: 95%;
    height: 100%;
    padding: 0px;
    margin: 3px 4px;
    border: none;
    outline: none;
    color: black;
    font-weight: bold;
}

.videoBox {
  position: relative;
  width: 30%;
  padding: 2%;
}

#video-box {
  width: 100%;
  height: 20%;
}

.video-info {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
}

.link-row {
  display: flex;
  width: 100%;
}

.video-link {
  width: 90%;
}

.copy-video-link {
  width: 10%;
}

.copy-video-link span {
  margin-left: 5px;
}

.thumbnail-box {
  display: flex;
  flex-direction: column;
}

.thumbnail-box div {
  background-color: #F9F9F9;
  border: none;
  border-radius: 5px;
  width: 98%;
  height: auto;
  text-align: left;
  padding: 8px;
}

.select-list {
  width: 65%;
  height: 40px;
  margin: 5% 0;
  border: solid 1px black;
  border-radius: 15px;
  text-align: start;
  padding: 10px 20px;
  margin: 5% 0;
}

.div-btn {
    position: absolute;
    right: 3%;
    bottom: 15%;
}

.div-btn button {
    margin-right: 15px;
    border: none;
    border-radius: 25px;
    width: 120px;
    height: 45px;
    font-size: 18px;
}

.btn-post {
    background-color: #3EA6FF;
    color: black;
}

.btn-cancel {
    background-color: black;
    color: white;
}

</style>
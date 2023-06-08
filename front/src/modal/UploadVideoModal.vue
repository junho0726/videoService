<template>
    <div class="overlay" v-show="showModal"></div>

    <!--  파일 올리기 전  -->
    <div v-if="!isUploadFile" @drop="dropFile($event)" @dragover.prevent class="videoModal" v-show="showModal">
        <div class="modal-title">
            <span>동영상 업로드</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-contents">
            <button class="btn-upload"><img src="@/assets/upload_video.png"></button>
            <span>동영상 파일을 드래그 앤 드롭하여 업로드</span>
            <input type="file" ref="inputVideoFile" @change="insertVideoFile()" style="display: none;">
            <button class="btn-select"  @click="openFile()">파일 선택</button>
        </div>
    </div>

    <!--  파일 올린 후  -->
    <div v-if="isUploadFile" class="videoModal" v-show="showModal">
        <div class="modal-title">
            <span>{{ videoInfo.title }}</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-file-info-box">
            <div class="modal-file-info">
                <br>
                <div class="file-title">
                    <p>제목(필수 항목)</p>
                    <textarea type="text" v-model="videoInfo.title"/>
                </div>
                <br>
                <div class="file-contents">
                    <p>설명</p>
                    <textarea type="text" v-model="videoInfo.contents" placeholder="시청자에게 동영상에 대해 이야기하기(채널을 멘션하려면 @ 입력)"/>
                </div>
                <div class="thumbnail-box">
                    <h5>미리보기 이미지</h5>
                    <div>
                      <input type="file" ref="inputThumbFile" @change="insertThumbFile()" style="display: none;">
                      <span v-if="!isUploadThumb" @click="openThumbFile()">썸네일 업로드</span>
                      <img v-if="isUploadThumb" class="img-thumbnail" :src="thumbInfo.link">
                    </div>
                </div>
                <div class="video-list-box">
                  <h5>재생목록</h5>
                  <div>
                    <span>동영상을 1개 이상의 재생목록에 추가하세요. 시청자가 내 콘텐츠를 더 빨리 발견하는 데 도움이 될 수 있습니다.</span>
                  </div>
                  <button class="select-list" @click="handlePlayListModal()">
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
                  poster="@/assets/my_logo.png">
                  <source :src="videoInfo.link" type="video/mp4"/>
                </video>
                <div class="video-info">
                  <div>
                    <p>동영상 링크</p>
                    <div class="link-row">
                      <div class="video-link">
                        <a :href="videoInfo.link" target="_blank" ref="linkForCopy">{{ videoInfo.link }}</a>
                      </div>
                      <div class="copy-video-link">
                        <span @click="copyLink()">Link</span>
                      </div>
                    </div>
                  </div>
                  <br>
                  <div>
                    <p>파일 이름</p>
                    <span>{{ videoInfo.name }}</span>
                  </div>
                    <div class="div-btn">
                        <button class="btn-cancel" @click="$emit('closeModal')">취소</button>
                        <button class="btn-post" @click="post()">게시</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
  <PlayListModal :show-modal="isShowPlayListModal" @close-modal="handlePlayListModal()"/>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import store from "@/store";
import instance from "@/api/axios";
import PlayListModal from "@/modal/PlayListModal.vue";

defineProps({
    showModal: Boolean
});


let inputVideoFile = ref();
let inputThumbFile = ref();
let linkForCopy = ref();
let correctFile = ref(false);
let isUploadFile = ref(false);
let isUploadThumb = ref(false);
let isShowPlayListModal = ref(false);
let videoInfo = ref({
  fileSeq: '',
  name: '',
  title: '',
  contents: '',
  link: ''
});

let thumbInfo = ref({
  fileSeq: '',
  name: '',
  title: '',
  contents: '',
  link: ''
});

function dropFile(event) {
  event.preventDefault();
  uploadFile(event.dataTransfer.files[0]);
}

function insertVideoFile() {
  uploadFile(inputVideoFile.value.files[0]);
}

function insertThumbFile()   {
  uploadFile(inputThumbFile.value.files[0]);
}

async function uploadFile(file) {
  await axios.post('/api/video/fileInsert ', {
      file: file
  }, {
      headers: {
          'Content-Type': 'multipart/form-data',
          "Access_Token": localStorage.getItem('token')
      }
  }).then(value => {
      let result = value.data;
      if(result.code == "0000") {
        if(result.data.fileOriginName.substring(result.data.fileOriginName.lastIndexOf('.') + 1, result.data.fileOriginName.lastIndex) === 'mp4') {
          console.log(result.data.fileOriginName.substring(result.data.fileOriginName.lastIndexOf('.') + 1, result.data.fileOriginName.lastIndex));
          setVideoInfo(result);
        } else {
          console.log(result.data.fileOriginName.substring(result.data.fileOriginName.lastIndexOf('.') + 1, result.data.fileOriginName.lastIndex));
          setThumbInfo(result);
        }
      } else {
        alert('오류 발생');
      }
  }).catch(reason => {
      console.log(reason);
  });
}

function setVideoInfo(responseFile) {
    if(checkVideoType(responseFile)) {
      let file = responseFile.data;
        videoInfo.value.name = file.fileOriginName;
        videoInfo.value.title = file.fileOriginName.substring(0, file.fileOriginName.lastIndexOf('.'));
        videoInfo.value.link = file.fileFullPath;
        videoInfo.value.fileSeq = file.fileSeq;
        isUploadFile.value = true;
    } else {
        alert('확장자를 확인하거나 재시도 해주세요.');
    }
}

function setThumbInfo(responseFile) {
  if(checkThumbType(responseFile)) {
    let file = responseFile.data;
    thumbInfo.value.name = file.fileOriginName;
    thumbInfo.value.title = file.fileOriginName.substring(0, file.fileOriginName.lastIndexOf('.'));
    thumbInfo.value.link = file.fileFullPath;
    thumbInfo.value.fileSeq = file.fileSeq;
    isUploadThumb.value = true;
  } else {
    alert('확장자를 확인하거나 재시도 해주세요.');
  }
}

function openFile() {
    inputVideoFile.value.click();
}

function openThumbFile() {
    inputThumbFile.value.click();
}

function checkVideoType(file) {
    let resFile = file.data.fileOriginName;
    if(resFile.substring(resFile.lastIndexOf('.') + 1, resFile.lastIndex) === "mp4") {
        correctFile.value = true;
        return true;
    } else {
        return false;
    }
}

function checkThumbType(file) {
  let resFile = file.data.fileOriginName;
  if(get확장자(resFile) === "png" || get확장자(resFile) === "jpg" || get확장자(resFile) === "jpeg") {
    correctFile.value = true;
    return true;
  } else {
    return false;
  }
}

function copyLink() {
    let copyLink = document.createElement('input');
    copyLink.value = videoInfo.value.link;
    document.body.append(copyLink);
    copyLink.select();
    document.execCommand('copy');
    document.body.removeChild(copyLink);
    alert('복사 되었습니다.');
}

function post() {
    if(videoInfo.value.title.trim() == "") {
      alert("제목은 필수 항목란입니다.");
    } else {
        instance.post("/api/video/videoInsert",
            {
                    "title" : videoInfo.value.title,
                    "content" : videoInfo.value.contents,
                    "file" : { "fileSeq" : videoInfo.value.fileSeq }
                }).then(value => {
                  let result = value.data;
                  if(result.code === "0000") {
                    instance.post('/api/video/thumbnailInsert',
                        {
                          "videoSeq" : result.data.videoSeq,
                          "thumbnail" : {
                            "filePath" : thumbInfo.value.link,
                            "fileFullPath" : thumbInfo.value.link,
                            "fileOriginName" : thumbInfo.value.title,
                            "fileName" : thumbInfo.value.name,
                            "file" : { "fileSeq" : thumbInfo.value.fileSeq }
                          }
                        }).then(data => {
                            if(data.data.code == '0000') {
                              alert("성공적으로 등록되었습니다.");
                              isUploadFile.value = false;
                              location.reload();
                            } else {
                              alert('예기치 못한 오류가 발생했습니다.');
                            }
                        }).catch(reason => {
                          alert('예기치 못한 오류가 발생했습니다.');
                        })
                  } else {
                    alert('예기치 못한 오류가 발생했습니다.');
                  }
                }).catch(reason => {
                    console.log(reason);
                    alert('예기치 못한 오류가 발생했습니다.');
                })
    }
}

function get확장자(fileOriginName) {
  return fileOriginName.substring(fileOriginName.lastIndexOf('.') + 1, fileOriginName.lastIndex).toLowerCase();
}

function handlePlayListModal() {
    isShowPlayListModal.value = !isShowPlayListModal.value;
}


</script>

<style scoped>

h5 {
  margin-bottom: 8px;
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

.select-list {
  width: 65%;
  height: 40px;
  margin: 5% 0;
  border: solid 1px black;
  border-radius: 15px;
  text-align: start;
  padding: 10px 20px;
  margin: 2% 0;
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

.img-thumbnail {
    width: 150px;
    height: 80px;
}

</style>
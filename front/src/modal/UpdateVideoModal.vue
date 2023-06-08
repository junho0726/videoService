<template>
    <div class="overlay" v-if="showModal"></div>

    <div class="videoModal" v-if="showModal">
        <div class="modal-title">
            <span>{{ videoInfo.videoTitle }}</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-file-info-box">
            <div class="modal-file-info">
                <br>
                <div class="file-title">
                    <p>제목(필수 항목)</p>
                    <textarea type="text" v-model="videoInfo.videoTitle"/>
                </div>
                <br>
                <div class="file-contents">
                    <p>설명</p>
                    <textarea type="text" v-model="videoInfo.videoContent" placeholder="시청자에게 동영상에 대해 이야기하기(채널을 멘션하려면 @ 입력)"/>
                </div>
                <div class="thumbnail-box">
                    <h5>미리보기 이미지</h5>
                    <div>
                      <input type="file" ref="inputThumbFile" style="display: none;">
                      <span v-if="!isUploadThumb">썸네일 업로드</span>
                      <img v-if="isUploadThumb" class="img-thumbnail" :src="videoInfo.thumbnailFullPath">
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
                  preload="auto"
                  :poster="videoInfo.thumbnailFullPath">
                  <source :src="videoInfo.fileFullPath" type="video/mp4"/>
                </video>
                <div class="video-info">
                  <div>
                    <p>동영상 링크</p>
                    <div class="link-row">
                      <div class="video-link">
                        <a :href="videoInfo.fileFullPath" target="_blank" ref="linkForCopy">{{ videoInfo.fileFullPath }}</a>
                      </div>
                      <div class="copy-video-link">
                        <span @click="">Link</span>
                      </div>
                    </div>
                  </div>
                  <br>
                  <div class="file-name-div">
                    <p>파일 이름</p>
                    <span>{{ videoInfo.fileName }}</span>
                  </div>
                    <div class="div-btn">
                        <button class="btn-cancel" @click="$emit('closeModal')">취소</button>
                        <button class="btn-post" @click="update()">수정</button>
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
import instance from "@/api/axios";

let props = defineProps({
    showModal: Boolean,
    videoSeq: Number
});

let inputThumbFile = ref();
let linkForCopy = ref();
let isUploadThumb = ref(true);
let videoInfo = ref({});

instance.get('/api/video/findDetail/' + props.videoSeq).then(value => {
    console.log(value.data.data)
    if(value.data.code === '0000') {
        videoInfo.value = value.data.data;
    } else {
        alert('예상치 못한 오류 발생');
    }
}).catch(reason => {
    console.log(reason);
})

function update() {
    instance.post('/api/video/update', {
        'videoSeq' : videoInfo.value.videoSeq,
        'videoTitle' : videoInfo.value.videoTitle,
        'videoContent' : videoInfo.value.videoContent
    }).then(value => {
        if (value.data.code == '0000') {
            alert('성공적으로 수정되었습니다.');
            location.reload();
        } else {
            alert('예기치 못 한 오류가 발생');
        }
    }).catch(reason => {
        console.log(reason);
        alert('예기치 못 한 오류가 발생');
    })
}
</script>

<style scoped>

h5 {
    text-align: start;
    margin-bottom: 8px;
}

.modal-contents span {
  display: block;
  margin: 3% 0;
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
    text-align: start;
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
  margin: 3% 0;
  border: solid 1px black;
  border-radius: 15px;
  text-align: start;
  padding: 10px 20px;
}

.div-btn {
    position: absolute;
    right: 3%;
    bottom: 1%;
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

.video-list-box {
    text-align: start;
}

.file-name-div {
    align-self: start;
}
</style>
<template>
    <div class="overlay" v-show="showModal"></div>

    <!--  파일 올리기 전  -->
    <div v-if="!correctFile" @drop="handleDrop" @dragover.prevent class="videoModal" v-show="showModal">
        <div class="modal-title">
            <span>동영상 업로드</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-contents">
            <button class="btn-upload"><img src="@/assets/upload_video.png"></button>
            <span>동영상 파일을 드래그 앤 드롭하여 업로드</span>
            <input type="file" ref="inputFile" style="display: none;">
            <button class="btn-select"  @click="openFile()">파일 선택</button>
        </div>
    </div>

    <!--  파일 올린 후  -->
    <div v-if="correctFile" class="videoModal" v-show="showModal">
        <div class="modal-title">
            <span>{{ fileInfo.title }}</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-file-info-box">
            <div class="modal-file-info">
                <br>
                <div class="file-title">
                    <p>제목(필수 항목)</p>
                    <input type="text" v-model="fileInfo.title">
                </div>
                <br>
                <div class="file-contents">
                    <p>설명</p>
                    <input type="text" v-model="fileInfo.contents" placeholder="시청자에게 동영상에 대해 이야기하기(채널을 멘션하려면 @ 입력)">
                </div>
            </div>
            <div class="videoBox">
                <video controls width="250px">
                    <source src="C:/Users/user/Documents/카카오톡 받은 파일/KakaoTalk_20230518_172717609.mp4">
                </video>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref} from "vue";

defineProps({
    showModal: Boolean
});

let inputFile = ref();
let correctFile = ref(false);
let fileInfo = ref({
   name: '',
   title: '',
   contents: ''
});

function handleDrop(e) {
    e.preventDefault();
    let file = e.dataTransfer.files[0];
    if(checkType()) {
        fileInfo.value.name = file.name;
        fileInfo.value.title = file.name.substring(0, file.name.lastIndexOf('.'));
    }
}

function openFile() {
    inputFile.value.click();
};

function checkType() {
    console.log('대충 체크함');
    correctFile.value = true;
    return true;
}

</script>

<style scoped>

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
    height: 80%;
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
    width: 65%;
}

.file-title {
    border: solid 1px black;
    border-radius: 5px;
    width: 100%;
    height: 70px;
}

.file-contents {
    border: solid 1px black;
    border-radius: 5px;
    width: 100%;
    height: 170px;
}

.modal-file-info-box p {
    margin: 3px 4px;
    color: slategrey;
}

.modal-file-info-box input {
    width: 98%;
    height: auto;
    padding: 0px;
    margin: 3px 4px;
    border: none;
    outline: none;
    color: black;
    resize: none;
}

.videoBox {

}
</style>
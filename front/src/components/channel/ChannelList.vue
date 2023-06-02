<template>
    <div class="contents">
        <div class="div-none-video" v-if="isEmptyList">
            <img src="/none_video.png">
            <h2>재생목록을 업로드하여 시작하기</h2>
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
    </div>
</template>

<script setup>
import {ref} from "vue";

let isEmptyList = ref(true);
let showModal = ref(false);

function handelModal() {
    showModal.value = !showModal.value;
}

</script>

<style scoped>

</style>
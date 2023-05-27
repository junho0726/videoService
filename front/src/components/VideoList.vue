<template>
    <div class="video-box" :class="{ 'show-side-bar-video-box':showSideBar }" v-for="video in videoList">
        <video
                id="video"
                class="video-js"
                controls
                preload="auto"
                :poster="video.thumbnailFullPath"
                @click="videoDetail(video.videoSeq)">
            <source :src="video.fileFullPath" type="video/mp4"/>
        </video>
        <div class="video-info-box" @click="videoDetail(video.videoSeq)">
            <img class="profile-img" src="/basic_profile.png">
            <div class="video-info-div">
                <span class="title-span">{{ video.videoTitle }}</span>
                <span>{{ video.channelName }}</span>
                <span>조회수 {{ video.videoCount }}회</span>
            </div>
        </div>
    </div>
</template>

<script setup>
  import router from "@/router";

  defineProps({
      videoList: [],
      showSideBar: Boolean
  })

  function videoDetail(videoSeq) {
      router.push({ name: 'videoDetail', params: { seq: videoSeq } });
  }

</script>

<style scoped>
.video-box {
    width: 19%;
    margin: 3% 0.5%;
    height: 250px;
}

.show-side-bar-video-box {
    width: 24%;
    margin: 3% 0.5%;
    height: 250px;
}

#video {
    width: 100%;
    height: 70%;
    border-radius: 15px;
}

.video-info-box {
    display: flex;
}

.video-info-div {
    display: flex;
    flex-direction: column;
    text-align: left;
    margin: 0 1.5%;
}

.video-info-div span {
    margin: 0.1% 0;
    font-size: 12px;
}

.title-span {
    font-weight: bold !important;
    font-size: 15px !important;
}
</style>
<template>
    <div>
        <Header @show-sidebar="showSidebar()"/>
        <div class="content-wrap">
            <SideBar v-if="isShowSidebar"/>
<!--            <div class="content" v-for="item in items" key="status">-->
<!--              <div class="video-box">-->
<!--                <video-->
<!--                    id="video"-->
<!--                    class="video-js"-->
<!--                    controls-->
<!--                    preload="auto"-->
<!--                    poster="@/assets/my_logo.png">-->
<!--                  <source :src="fileInfo.link" type="video/mp4"/>-->
<!--                </video>-->
<!--              </div>-->
<!--            </div>-->
        </div>
    </div>
</template>

<script setup>
import Header from '@/layout/Header.vue';
import SideBar from '@/layout/SideBar.vue';
import { ref } from 'vue';
import axios from "axios";

let isShowSidebar = ref(false);

axios.get('/api/video/findAll').then(value => {
  console.log(value);
  console.log("!");
}).catch(reason => {
  console.log(reason);
  console.log("?");
})

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style>
.content-wrap {
    display: flex;
}

.content {
  display: flex;
}

.video-box {
  width: 200px;
  height: 180px;
}

#video {
  width: 100%;
  height: 70%;
}
</style>
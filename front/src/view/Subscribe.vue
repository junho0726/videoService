<template>
    <div>
        <Header @show-sidebar="showSidebar()" @send-keyword="search" :is-show-search="false"/>
        <div class="content-wrap">
            <SideBar v-if="isShowSidebar"/>
            <div class="content" :class="{ 'show-side-bar-content' : isShowSidebar }">

            </div>
        </div>
    </div>
</template>

<script setup>
import Header from '@/layout/Header.vue';
import SideBar from '@/layout/SideBar.vue';
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

let isShowSidebar = ref(false)
let videoList = ref([]);

function search(q) {
    axios.get('/api/video/findAll?q=' + q).then(value => {
        let data = value.data;
        let dataList = data.data;
        videoList.value = [];
        if(data.code === "0000") {
            for(let i = 0; i < dataList.length; i++) {
                videoList.value.push(dataList[i]);
            }
            router.push('/');
        } else {
            alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
        }
    }).catch(reason => {
        console.log(reason);
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
    })
}
function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style scoped>

.content {
    width: 98%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 1%;
}

.show-side-bar-content {
    margin-left: 10%;
    width: 88%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 1%;
}

</style>
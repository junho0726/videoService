<template>
    <div>
        <Header @show-sidebar="showSidebar()"/>
        <div class="content-wrap">
            <SideBar v-if="isShowSidebar"/>
            <div class="category-row" v-for="category in categoryList">
              <input class="category" :value="category.categoryName">
            </div>
            <div class="content" :class="{ 'show-side-bar-content':isShowSidebar }">
              <Video :video-list="videoList" :show-side-bar="isShowSidebar"/>
            </div>
        </div>
    </div>
</template>

<script setup>
import Header from '@/layout/Header.vue';
import SideBar from '@/layout/SideBar.vue';
import { ref } from 'vue';
import axios from "axios";
import Video from "@/components/VideoList.vue";

let isShowSidebar = ref(false);
let categoryList = ref([]);
let videoList = ref([]);

axios.post('/api/category/findAll').then(value => {
        console.log(value);
    if(value.data.code === '0000') {
    } else {
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
    }
}).catch(reason => {
    console.log(reason);
    alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
})

axios.get('/api/video/findAll').then(value => {
    let data = value.data;
    let dataList = data.data;
    if(data.code === "0000") {
        for(let i = 0; i < dataList.length; i++) {
            videoList.value.push(dataList[i]);
        }
    } else {
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
    }
}).catch(reason => {
    alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
})

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style>

.category {
    width: 10%;
    height: 70px;
    background-color: #F2F2F2;
    color: black;
    border: none;
    border-radius: 15px;
}

.content-wrap {
    display: flex;
}

.content {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 3%;
}

.show-side-bar-content {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 1%;
}



</style>
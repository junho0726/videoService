<template>
    <div>
        <Header @show-sidebar="showSidebar()" :is-show-search="false"/>
        <div class="content-wrap">
            <SideBar v-if="isShowSidebar"/>
            <div class="content" :class="{ 'show-side-bar-content' : isShowSidebar }">
                <div class="subscribe-list" v-for="item in subscribeList">
                    <img class="subscribe-img" src="/basic_profile.png">
                    <div class="subscribe-info">
                        <div class="channel-col">

                        </div>
                    </div>
                </div>
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
import VideoList from "@/components/VideoList.vue";
import instance from "@/api/axios";

let isShowSidebar = ref(false)
let subscribeList = ref([]);

instance.get('/api/subscribe/list').then(value => {
    if(value.data.code == '0000') {
        let result = value.data.data;
        for (let i = 0; i < result.length; i++) {
            subscribeList.value.push(result[i]);
        }
        console.log(subscribeList.value);
    }
}).catch(reason => {
    console.log(reason);
})

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style scoped>

.content {
    width: 98%;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    padding: 1% 10%;
}

.show-side-bar-content {
    margin-left: 10%;
    width: 88%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 1%;
}

.subscribe-list {
    display: flex;
    margin: 1% 0;
}

.subscribe-box {
    display: flex;
    flex-direction: column;
    margin: 1% 0;
}

.subscribe-img {
    width: 150px;
}
</style>
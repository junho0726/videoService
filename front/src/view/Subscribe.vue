<template>
    <div>
        <Header @show-sidebar="showSidebar()" :is-show-search="false"/>
        <div class="content-wrap">
            <SideBar v-if="isShowSidebar"/>
            <div class="content" :class="{ 'show-side-bar-content' : isShowSidebar }">
                <h3>구독한 채널</h3>
                <div class="subscribe-list" v-for="item in subscribeList">
                    <div class="subscribe-row">
                        <img class="subscribe-img" src="/basic_profile.png">
                        <div class="subscribe-info">
                            <div class="channel-col">
                                <span>{{ item.channelName }}</span>
                                <span>구독 {{ item.subscribeCount }}명</span>
                            </div>
                        </div>
                    </div>
                    <div class="div-subscribe">
                        <button class="btn-subscribe" @click="cancelSubscribe(item.channelSeq)">구독 취소</button>
                    </div>
                </div>
                <br>
                <div class="line"></div>
                <br>
                <h3>시청한 영상</h3>
                <div class="video-list" v-for="item in videoList">
                    <div class="video-row">
                        <img class="video-img" src="/basic_profile.png">
                        <div class="subscribe-info">
                            <div class="channel-col">
                                <span>{{ item.channelName }}</span>
                                <span>구독 {{ item.subscribeCount }}명</span>
                            </div>
                        </div>
                    </div>
                    <div class="div-subscribe">
                        <button class="btn-subscribe" @click="cancelSubscribe(item.channelSeq)">구독 취소</button>
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
    }
}).catch(reason => {
    console.log(reason);
})

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

function cancelSubscribe(channelSeq) {
    if(localStorage.getItem('token') === null) {
        alert('로그인 후 이용해주세요.');
        router.push('/login');
    } else {
        instance.post('/api/subscribe/save', {
            'channel': {'channelSeq': channelSeq},
            'user': {'userSeq': localStorage.getItem('seq')}
        }).then(value => {
            subscribeList.value = [];
            if (value.data.code === '0000') {
                instance.get('/api/subscribe/list').then(value => {
                    if(value.data.code == '0000') {
                        let result = value.data.data;
                        for (let i = 0; i < result.length; i++) {
                            subscribeList.value.push(result[i]);
                        }
                    }
                }).catch(reason => {
                    console.log(reason);
                })
            }
        }).catch(reason => {
            console.log(reason);
        })
    }
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
    justify-content: space-between;
    margin: 1% 0;
}

.subscribe-row {
    display: flex;
}

.subscribe-img {
    width: 150px;
}

.channel-col {
    display: flex;
    flex-direction: column;
    width: 300px;
    height: 100%;
    text-align: left;
    justify-content: left;
}

.channel-col span {
    margin: 5% 5%;
    font-size: 22px;
    font-weight: bold;
}

.div-subscribe {
    margin: 1%;
    text-align: center;
    align-self: end;
}

</style>
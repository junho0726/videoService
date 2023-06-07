<template>
        <Header @show-sidebar="showSidebar()" :is-in-channel="false"/>
        <SideBar v-if="isShowSidebar"/>
                <div class="contents-wrap">
                        <div class="contents">
                                <div class="profile-box">
                                        <img class="channel-profile-img" src="/basic_profile.png">
                                        <div class="profile-detail">
                                                <span class="profile-name">{{ channelInfo.userName }}</span>
                                                <div class="profile-col">
                                                        <span>{{ channelInfo.channelName }}</span>
                                                        <span v-if="channelInfo.subscribeCount == 0">구독자 없음</span>
                                                        <span v-if="channelInfo.subscribeCount != 0">구독자 {{ channelInfo.subscribeCount }}명</span>
                                                        <span v-if="channelInfo.videoEntityListSize == 0">동영상 없음</span>
                                                        <span v-if="channelInfo.videoEntityListSize != 0">동영상 {{ channelInfo.videoEntityListSize }}개</span>
                                                </div>
                                        </div>
                                </div>
                                <div class="channel-nav">
                                        <span :class="{ active : isActive[0] }" @click="toggleMenu(0)">홈</span>
                                        <span :class="{ active : isActive[1] }" @click="toggleMenu(1)">재생목록</span>
                                        <span :class="{ active : isActive[2] }" @click="toggleMenu(2)">정보</span>
                                        <div class="nav-line"></div>
                                </div>
                                <ChannelHome v-if="isActive[0]"/>
                                <ChannelList v-if="isActive[1]"/>
                                <ChannelInfo v-if="isActive[2]"/>
                        </div>
                </div>

</template>

<script setup>
import Header from '@/layout/Header.vue';
import { ref } from "vue";
import ChannelHome from "@/components/channel/ChannelHome.vue";
import ChannelList from "@/components/channel/ChannelList.vue";
import ChannelInfo from "@/components/channel/ChannelInfo.vue";
import SideBar from "@/layout/SideBar.vue";
import instance from "@/api/axios";

let isActive = ref([true, false, false]);
let isShowSidebar = ref(false);
let channelInfo = ref({});

let props = defineProps({
        channelSeq: String
})

instance.get('/api/channel/detail/' + props.channelSeq
).then(value => {
        if (value.data.code == '0000') {
                channelInfo.value = value.data.data;
        } else {
                alert('재로그인 후 진행해주세요.');
        }
}).catch(reason => {
        alert('재로그인 후 진행해주세요.');
})

function toggleMenu(index) {
        for (let i = 0; i < isActive.value.length; i++) {
                if(i === index) {
                        isActive.value[i] = true;
                } else {
                        isActive.value[i] = false;
                }
        }
}

function showSidebar() {
        isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style>
.contents-wrap {
        margin: 0% 15%;
}

.contents {
        display: flex;
        flex-direction: column;
}

.profile-box {
        display: flex;
        margin: 2% 0;
}

.profile-detail {
        display: flex;
        flex-direction: column;
        margin-top: 15px;
        margin-left: 15px;
}

.profile-name {
        font-size: 25px;
}

.channel-nav span {
        margin: 0% 2%;
        color: slategrey;
}

.nav-line {
        margin-top: 1%;
        border-top: solid 1px slategrey;
}

.active {
        color: black !important;
        font-weight: bold;
}

.profile-col {
        display: flex;
        flex-direction: column;
}
</style>

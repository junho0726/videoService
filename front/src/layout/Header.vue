<template>
    <div class="header">
        <div class="header-div">
            <img class="logo-img" src="@/assets/hamberger.png" @click="$emit('show-sidebar')">
            <router-link to="/"><img class="logo-img" src="@/assets/my_logo_with_text.png" @click=""></router-link>
        </div>

        <div class="profile-div" @mouseover="over()" @mouseleave="leave()">
            <img class="profile-img" src="/basic_profile.png">
            <div class="profile-menu" v-if="showProfileInfo">
                <router-link v-if="!isLogin" to="/login">LOGIN</router-link>
                <router-link v-if="!isLogin" to="/join">JOIN</router-link>
                <router-link v-if="isLogin" to="/mypage">MY PAGE</router-link>
                <router-link v-if="isLogin" to="/mychannel">MY CHANNEL</router-link>
                <span v-if="isLogin" @click="logout()">LOGOUT</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import {computed, ref} from 'vue';
import store from "@/store";

let props = defineProps({
  isInChannel : false
})

let showProfileInfo = ref(false);
let isLogin = computed(() => {
    return store.getters['user/getToken'] !== null;
});

function over() {
    showProfileInfo.value = true;
}

function leave() {
    showProfileInfo.value = false;
}

function logout() {
    alert('로그아웃 되었습니다.');
    location.href = '/login';
}

</script>

<style>
.header {
    background-color: white;
    padding: 2% 3%;
    display: flex;
    justify-content: space-between;
}

.header-div {
    display: flex;
    margin-right: 3%;
}

.logo-img {
    width: 80px;
    height: 40px;
    margin-right: 30%;
}

.profile-menu {
    display: flex;
    flex-direction: column;
}

</style>
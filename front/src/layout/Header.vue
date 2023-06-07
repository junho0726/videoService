<template>
    <div class="header">
        <div class="header-div">
            <img class="logo-img" src="@/assets/hamberger.png" @click="$emit('show-sidebar')">
            <router-link to="/"><img class="logo-img" src="@/assets/my_logo_with_text.png"></router-link>
        </div>
        <div class="search-div" v-if="isShowSearch">
          <input v-model="q" class="input-search" type="text" placeholder="뭐가 궁금하노?">
          <img class="search-img" src="/search.png" @click="sendKeyword">
        </div>
        <div class="profile-div">
            <img class="profile-img" src="/basic_profile.png" @click="show()">
            <div class="profile-menu" :class="{ 'is-login-profile-menu' : isLogin }" v-if="showProfileInfo">
                <router-link v-if="!isLogin" to="/login">LOGIN</router-link>
                <div v-if="!isLogin" class="line"></div>
                <router-link v-if="!isLogin" to="/join">JOIN</router-link>
                <router-link v-if="isLogin" to="/mypage">MY PAGE</router-link>
                <div v-if="isLogin" class="line"></div>
                <router-link v-if="isLogin" to="/mychannel/0">MY CHANNEL</router-link>
                <div v-if="isLogin" class="line"></div>
                <span v-if="isLogin" @click="logout()">LOGOUT</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import {computed, ref} from 'vue';
import store from "@/store";
import router from "@/router";

let props = defineProps({
    isInChannel : Boolean,
    isShowSearch : Boolean
})

let emit = defineEmits([])

let sendKeyword = () => {
    emit('sendKeyword', q.value)
}

let q = ref('');
let showProfileInfo = ref(false);
let isLogin = computed(() => {
    return localStorage.getItem('token') !== null;
});

function show() {
    showProfileInfo.value = !showProfileInfo.value;
}

function logout() {
    alert('로그아웃 되었습니다.');
    localStorage.removeItem('token');
    localStorage.removeItem('seq');
    localStorage.removeItem('channelSeq');
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
    width: 25%;
    margin-right: 3%;
}

.logo-img {
    width: 80px;
    height: 40px;
    margin-right: 30%;
}

.profile-menu {
    border: solid 1px #F2F2F2;
    border-radius: 15px;
    padding: 3%;
    position: absolute;
    right: -3%;
    display: flex;
    flex-direction: column;
    text-align: center;
    z-index: 9999;
}

.is-login-profile-menu {
    border: solid 1px #F2F2F2;
    border-radius: 15px;
    padding: 3%;
    position: absolute;
    right: -10%;
    display: flex;
    flex-direction: column;
    text-align: center;
}

.profile-menu a {
    text-decoration: none;
    color: black;
    font-size: 18px;
}

.profile-div {
    position: relative;
      text-align: right;
      width: 25%;
}

.search-div {
  display: flex;
  width: 40%;
  position: relative;
}

.input-search {
  align-self: center;
  background-color: white;
  border: solid 1px #F2F2F2;
  border-radius: 35px;
  width: 100%;
  height: 100%;
  padding: 0 3%;
}

.search-img {
  position: absolute;
  width: 35px;
  height: 35px;
  top: 15%;
  right: 3%;
}

</style>
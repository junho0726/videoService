<template>
    <div class="header">
        <div class="header-div">
            <img class="logo-img" src="@/assets/hamberger.png" @click="$emit('show-sidebar')">
            <router-link to="/"><img class="logo-img" src="@/assets/my_logo_with_text.png" @click=""></router-link>
        </div>

        <div class="profile-div" @mouseover="over()" @mouseleave="leave()">
            <img class="profile-img" src="@/assets/basic_profile.png">
            <div class="profile-menu" v-if="showProfileInfo">
                <div v-if="!isLogin">
                    <router-link to="/login">LOGIN</router-link>
                    <router-link to="/join">JOIN</router-link>
                </div>
                <div v-if="isLogin">
                    <router-link to="#" v-if="isLogin">LOGOUT</router-link>                -
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import store from '@/store';

let showProfileInfo = ref(false);
let isLogin = ref(false);

onMounted(() => {
    if(store.getters['user/getToken'] != null) {
        console.log(store.getters['user/getToken']);
        isLogin.value = true;
    }
});

function over() {
    showProfileInfo.value = true;
}

function leave() {
    showProfileInfo.value = false;
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

.profile-img {
    width: 55px;
    height: 50px;
}

.profile-menu {
    display: flex;
}

</style>
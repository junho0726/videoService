<template>
    <Header />
    <div class="contents">
    <h1>로그인</h1>
        <br>
        <div>
            <input type="text" id="id" v-model="id" placeholder="아이디를 입력해주세요.">
        </div>
        <span>{{ checkEmptyId }}</span>
        <br>
        <div>
            <input type="text" id="pw" v-model="pw" placeholder="비밀번호를 입력해주세요.">
        </div>
        <span>{{ checkEmptyPw }}</span>
        <br>
        <div>
            <button class="btn-login" type="button" @click="login">Login</button>
        </div>
        <br>
        <br>
        <div class="join-box">
            <span>아직 계정이 없으신가요?</span>
            <br>
            <router-link to="/join"><button type="button">회원가입 하러 가기</button></router-link>
        </div>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import store from '@/store';
import { ref } from 'vue';
import instance from "@/api/axios";
import router from "@/router";

let id = ref('');
let pw = ref('');

let checkEmptyId = ref('');
let checkEmptyPw = ref('');

let login = async () => {

    if(checkEmpty()) {
        try {
            let response = await instance.post('/api/user/login', {
                id: id.value,
                pw: pw.value
            });
            console.log(response.data);
            if(response.data.code == "0000") {
                localStorage.setItem('token', response.data.data.accessToken);
                localStorage.setItem('seq', response.data.data.userSeq);
                await store.dispatch('user/setToken', response.data.data.accessToken);
                await store.dispatch('user/setUserSeq', response.data.data.userSeq);
                await router.push('/');
                alert("환영합니다.");
            } else {
                alert("아이디 혹은 비밀번호를 확인해주세요.");
            }
        } catch (error) {
            console.error(error);
        }
    }
};

function checkEmpty() {
    if(id.value == '') {
        checkEmptyId.value = '아이디를 입력해주세요.';
        return false;
    } else {
        checkEmptyId.value = '';
    }
    if(pw.value == '') {
        checkEmptyPw.value = '비밀번호를 입력해주세요.';
        return false;
    } else {
        checkEmptyPw.value = '';
    }
    return true;
}

</script>

<style scoped>

.contents {
    text-align: center;
    margin-top: 5%;
}

.contents input {
    width: 13%;
    margin-right: 1%;
}

.btn-login {
    width: 8%;
    margin-right: 1%;
}

.join-box {
    display: flex;
    flex-direction: column;
}

.join-box button {
    width: 13%;
    margin-right: 1%;
}

</style>
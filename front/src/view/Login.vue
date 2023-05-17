<template>
    <Header/>

    <h1>Login 페이지</h1>

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
        <button type="button" @click="login">Login</button>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import axios from "axios";
import store from '@/store';
import { ref } from 'vue';

let id = ref('');
let pw = ref('');

let checkEmptyId = ref('');
let checkEmptyPw = ref('');

let login = async () => {

    if(checkEmpty()) {
        try {
            if(store.getters['user/getToken'] != null) {
                let response = await axios.post('/api/user/login', {
                    id: id.value,
                    pw: pw.value
                },
                {
                    headers: {
                        'Access_Token': store.getters['setToken']
                    }
                }
                );
                if(response.data.code == "0000") {
                    alert("토큰 인증 완료");
                } else {
                    console.log();
                    alert("토큰 인증 실패");
                }
            } else {
                let response = await axios.post('/api/user/login', {
                    id: id.value,
                    pw: pw.value
                });
                if(response.data.code == "0000") {
                    store.dispatch('user/setToken', response.data.data.accessToken);
                    alert("환영합니다.");
                } else {
                    alert("아이디 혹은 비밀번호를 확인해주세요.");
                }
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

<style>
</style>
<template>
    <Header/>

    <h1>Login 페이지</h1>

    <div>
        <input type="text" id="id" v-model="id" placeholder="아이디를 입력해주세요.">
    </div>
    <div>
        <input type="text" id="pw" v-model="pw" placeholder="비밀번호를 입력해주세요.">
    </div>
    <div>
        <button type="button" @click="login">Login</button>
        <button type="button">Join</button>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import axios from "axios";
import { ref } from 'vue';

let id = ref('');
let pw = ref('');

let login = async () => {
    try {
        let response = await axios.post('/api/login', {
            id: id.value,
            pw: pw.value
        });
        console.log(response.data);
    } catch (error) {
        console.error(error);
    }
};

</script>

<style>
</style>
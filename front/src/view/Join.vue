<template>
    <Header/>

    <h1>Join 페이지</h1>

    <div>
        <input type="text" id="id" v-model="id" placeholder="아이디를 입력해주세요.">
    </div>
    <div>
        <input type="text" id="pw" v-model="pw" placeholder="비밀번호를 입력해주세요.">
    </div>
    <div>
        <input type="text" id="re-pw" v-model="rePw" placeholder="비밀번호를 한번 더 입력해주세요.">
    </div>
    <div>
        <input type="text" id="name" v-model="name" placeholder="이름을 입력해주세요.">
    </div>
    <div>
        <input type="text" id="email" v-model="email" placeholder="이메일을 입력해주세요.">
    </div>
    <div>
        <input type="text" id="tel" v-model="tel" placeholder="연락처를 입력해주세요.">
    </div>
    <div>
        <button type="button">Login</button>
        <button type="button" @click="change()">Join</button>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import axios from "axios";
import { ref } from 'vue';

let id = ref('');
let pw = ref('');
let rePw = ref('');
let name = ref('');
let email = ref('');
let tel = ref('');



let join() = async () => {
    try {
        let response = await axios.post('/api/joinProc', {
            id: id.value,
            pw: pw.value,
            rePw: rePw.value,
            name: name.value,
            email: email.value,
            tel: tel.value
        });
        console.log(response.data);
    } catch (error) {
        console.error(error);
    }
};

</script>

<style>
</style>
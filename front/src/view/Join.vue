<template>
    <Header/>

    <h1>Join 페이지</h1>

    <div>
        <input type="text" id="id" name="id" placeholder="아이디를 입력해주세요.">
    </div>
    <div>
        <input type="text" id="pw" name="pw" placeholder="비밀번호를 입력해주세요.">
    </div>
    <div>
        <input type="text" id="re-pw" name="re-pw" placeholder="비밀번호를 한번 더 입력해주세요.">
    </div>
    <div>
        <input type="text" id="name" name="name" placeholder="이름을 입력해주세요.">
    </div>
    <div>
        <input type="text" id="email" name="email" placeholder="이메일을 입력해주세요.">
    </div>
    <div>
        <input type="text" id="tel" name="tel" placeholder="연락처를 입력해주세요.">
    </div>
    <div>
        <button type="button">Login</button>
        <button type="button">Join</button>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import axios from "axios";

axios.post('/api/join', {

})

</script>

<style>
</style>
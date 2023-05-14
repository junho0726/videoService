<template>
    <Header/>

    <h1>Login 페이지</h1>

    <div>
        <input type="text" id="id" name="id" placeholder="아이디를 입력해주세요.">
    </div>
    <div>
        <input type="text" id="pw" name="pw" placeholder="비밀번호를 입력해주세요.">
    </div>
    <div>
        <button type="button">Login</button>
        <button type="button">Join</button>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue"

</script>

<style>
</style>
<template>
    <div class="side-bar">
<!--        <a href="#">홈</a>-->
<!--        <div class="line"></div>-->
        <a href="#">보관함</a>
        <div class="line"></div>
        <router-link to="/subscribe">구독</router-link>
    </div>
</template>

<script setup>
</script>

<style>
.side-bar {
    position: absolute;
    display: flex;
    flex-direction: column;
    width: 10%;
    height: auto;
    z-index: 9999;
    background-color: white;
    text-align: center;
}

.side-bar a {
    text-decoration: none;
    color: black;
    font-size: 25px;
    margin: 3% 0;
}

.line {
    border-bottom: solid 1px #F2F2F2;
}
</style>
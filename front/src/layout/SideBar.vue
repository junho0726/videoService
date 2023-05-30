<template>
    <div class="side-bar">
<!--        <a href="#">홈</a>-->
<!--        <div class="line"></div>-->
        <a href="#">보관함</a>
        <div class="line"></div>
        <a href="#">구독</a>
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
    height: 100%;
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
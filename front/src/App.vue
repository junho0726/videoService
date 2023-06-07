<template>

    <router-view></router-view>

</template>

<script>

</script>

<style>
.channel-profile-img {
    width: 130px;
    height: 120px;
}

.profile-img {
    width: 55px;
    height: 50px;
}

.content-wrap {
    display: flex;
}

h2 {
    margin: 13px 0;
}

.line {
    border-bottom: solid 1px #F2F2F2;
}

.btn-subscribe {
    border: none;
    border-radius: 40px;
    background-color: black;
    color: white;
    font-size: 16px;
    width: 90px;
    height: 40px;
    margin: 0 2%;
}

.overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
}
</style>

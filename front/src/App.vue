<template>

    <router-view></router-view>

</template>

<script>

</script>

<style>
a {
    display: block;
    color: #3EA6FF !important;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

span {
    font-size: 12px;
    color: slategrey;
    margin: 5px;
}

.channel-profile-img {
    width: 130px;
    height: 120px;
}

.profile-img {
    width: 55px;
    height: 50px;
}

.contents {
    text-align: center;
    margin-top: 5%;
}

.contents input {
    width: 13%;
    margin-right: 1%;
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
    z-index: 9998;
}

/* 비디오 모달 */
.videoModal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    z-index: 9999;
    width: 60%;
    height: 90%;
    display: flex;
    flex-direction: column;
}

.modal-title {
    display: flex;
    justify-content: space-between;
    border-bottom: solid 1px black;
    padding-bottom: 10px;
}

.modal-title span {
    font-size: 22px;
    font-weight: bold;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.modal-title button {
    border: none;
    background-color: white;
    color: black;
    font-size: 22px;
}

.videoBox {
    position: relative;
    width: 30%;
    padding: 2%;
}

#video-box {
    width: 100%;
    height: 20%;
}

.video-info {
    width: 100%;
    height: auto;
    display: flex;
    flex-direction: column;
}

.link-row {
    display: flex;
    width: 100%;
}

.video-link {
    width: 90%;
}

.copy-video-link {
    width: 10%;
}

.copy-video-link span {
    margin-left: 5px;
}

.thumbnail-box {
    display: flex;
    flex-direction: column;
}

.thumbnail-box div {
    background-color: #F9F9F9;
    border: none;
    border-radius: 5px;
    width: 98%;
    height: auto;
    text-align: left;
    padding: 8px;
}
</style>

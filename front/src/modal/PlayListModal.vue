<template>
    <div class="overlay" v-if="showModal"></div>

    <div class="play-list-modal" v-if="showModal">
        <div class="modal-title">
            <span>재생목록을 선택해보세요 !</span>
            <button @click="$emit('closeModal')">X</button>
        </div>
        <div class="modal-content">

        </div>
    </div>
</template>

<script setup>

let props = defineProps({
    showModal: Boolean
});

</script>

<style scoped>

.play-list-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    z-index: 10000;
    width: 20%;
    height: 45%;
    display: flex;
    flex-direction: column;
    border: solid 3px #F2F2F2;
}

.play-list-scroll {
    border: 1px solid black;
    width: 90%;
    height: ;
    overflow-y: scroll;
}
</style>
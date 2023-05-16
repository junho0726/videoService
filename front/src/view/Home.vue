<template>
    <div>
        <Header @show-sidebar="showSidebar()"/>
        <div class="content">
            <SideBar v-if="isShowSidebar"/>
            I'm home
        </div>
    </div>
</template>

<script setup>
import Header from '@/layout/Header.vue';
import SideBar from '@/layout/SideBar.vue';
import { ref } from 'vue';

let isShowSidebar = ref(false);

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style>
.content {
    display: flex;
}
</style>
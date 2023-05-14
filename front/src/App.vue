<template>

    <router-view></router-view>

    <div>
        <router-link to="/">홈으로 가기</router-link>
    </div>

    <div>
        <router-link to="/login">login으로 가기</router-link>
    </div>

    <div>
        <router-link to="/join">join으로 가기</router-link>
    </div>
</template>

<script>
import {useRoute} from "vue-router";

export default {
  name: 'App',
  setup() {
    let route = useRoute();

    return {
        route
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

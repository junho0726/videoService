import { createWebHistory, createRouter } from "vue-router";
import App from "@/App.vue";
import Home from "@/view/Home.vue";
import Login from "@/view/Login.vue";
import Join from "@/view/Join.vue";
import MyChannel from "@/user/MyChannel.vue";
import MyPage from "@/user/MyPage.vue";
import store from "@/store";

// routes 배열 초기화
const routes = [
    {
        path: "/app", // App 컴포넌트의 하이라끼
        name: "app",  // 이름 지정
        component: App // 컴포넌트 선언
    },
    {
        path: "/",
        name: "home",
        component: Home
    },
    {
        path: "/login",
        name: "login",
        component: Login
    },
    {
        path: "/join",
        name: "join",
        component: Join
    },
    {
        path: "/mypage",
        name: "mypage",
        component: MyPage
    },
    {
        path: "/mychannel",
        name: "mychannel",
        component: MyChannel
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    console.log(to.name)
    if(to.name !== 'login' && to.name !== 'home' && to.name !== 'join') {
        if(store.getters['user/getToken'] == null) {
            next('/login');
        } else {
            next();
        }
    } else {
        next();
    }
})

export default router;
import { createWebHistory, createRouter } from "vue-router";
import App from "@/App.vue";
import Home from "@/view/Home.vue";
import Login from "@/view/Login.vue";
import Join from "@/view/Join.vue";
import MyChannel from "@/view/MyChannel.vue";
import MyPage from "@/view/MyPage.vue";
import store from "@/store";
import VideoDetail from "@/view/VideoDetail.vue";
import Subscribe from "@/view/Subscribe.vue";

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
    },
    {
        path: "/videoDetail/:seq",
        name: "videoDetail",
        component: VideoDetail,
        props: true
    },
    {
        path: "/subscribe",
        name: "subscribe",
        component: Subscribe
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {

    if(to.name !== 'login' && to.name !== 'home' && to.name !== 'join' && to.name !== 'videoDetail') {
        if(localStorage.getItem('token') == null) {
            next('/login');
        } else {
            next();
        }
    } else {
        next();
    }
})

export default router;
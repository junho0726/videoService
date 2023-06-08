<template>
    <Header :is-show-search="false"/>
    <div class="contents">
        <h1>회원 정보</h1>
        <br>
        <div>
            <span>아이디</span> <input type="text" id="id" v-model="userInfo.id" disabled>
        </div>
        <br>
        <div>
            <span>이름</span> <input type="text" id="name" v-model="userInfo.name" placeholder="이름을 입력해주세요." :disabled="!updateState">
        </div>
        <span>{{ checkEmptyName }}</span>
        <br>
        <div>
            <span>이메일</span> <input type="text" id="email" v-model="userInfo.email" placeholder="이메일을 입력해주세요." :disabled="!updateState">
        </div>
        <span>{{ checkEmptyEmail }}</span>
        <br>
        <div>
            <span>연락처</span> <input type="text" id="tel" v-model="userInfo.tel" placeholder="연락처를 입력해주세요." :disabled="!updateState">
        </div>
        <span>{{ checkEmptyTel }}</span>
        <br><br>
        <div>
            <button class="btn-info" type="button" @click="handelUpdateState()" v-if="updateState">취소</button>
            <button class="btn-info" type="button" @click="handelUpdateState()" v-if="!updateState">수정</button>
            <button class="btn-info" type="button" @click="update" v-if="updateState">완료</button>
        </div>
        <br>
        <div>
            <button class="btn-info" type="button">비밀번호 변경하기</button>
        </div>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import {ref} from 'vue';
import instance from "@/api/axios";

let checkEmptyName = ref('');
let checkEmptyEmail = ref('');
let checkEmptyTel = ref('');

let updateState = ref(false);
let userInfo = ref({});

// eslint-disable-next-line no-unused-vars
instance.post('/api/user/info').then(value => {
    if(value.data.code == '0000') {
        userInfo.value = value.data.data;
    }
}).catch(reason => {
    console.log(reason);
});

let update = async () => {
    if(checkEmpty()) {
        try {
            let response = await instance.post('/api/user/updateProc', {
                userSeq: userInfo.value.userSeq,
                id: userInfo.value.id,
                name: userInfo.value.name,
                pw: userInfo.value.pw,
                email: userInfo.value.email,
                tel: userInfo.value.tel
            });
            if(response.data.code == "0000") {
                alert('회원 정보 수정에 성공하셨습니다.');
                updateState.value = false;
            } else {
                alert('예기치 못한 에러가 발생했습니다.');
            }
        } catch (error) {
            console.error(error);
        }
    }
};

function handelUpdateState() {
    updateState.value = !updateState.value;
}

function checkEmpty() {
    if(userInfo.value.name == '') {
        checkEmptyName.value = '이름을 입력해주세요.'
        return false;
    } else {
        checkEmptyName.value = ''
    }
    if(userInfo.value.email == '') {
        checkEmptyEmail.value = '이메일을 입력해주세요.'
        return false;
    } else {
        checkEmptyEmail.value = ''
    }
    if(userInfo.value.tel == '') {
        checkEmptyTel.value = '연락처를 입력해주세요.'
        return false;
    } else {
        checkEmptyTel.value = ''
    }
    return true;
}

</script>

<style scoped>

.btn-info {
    width: 13%;
    height: 30px;
    margin-right: 1%;
    background-color: #3EA6FF;
    border: none;
    border-radius: 15px;
}
</style>
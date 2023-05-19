<template>
    <Header/>
    <div class="contents">
        <h1>회원 정보</h1>
        <br>
        <div>
            <input type="text" id="id" v-model="userInfo.id" disabled>
        </div>
        <br>
        <div>
            <input type="text" id="name" v-model="userInfo.name" placeholder="이름을 입력해주세요." :disabled="!updateState">
        </div>
        <span>{{ checkEmptyName }}</span>
        <br>
        <div>
            <input type="text" id="email" v-model="userInfo.email" placeholder="이메일을 입력해주세요." :disabled="!updateState">
        </div>
        <span>{{ checkEmptyEmail }}</span>
        <br>
        <div>
            <input type="text" id="tel" v-model="userInfo.tel" placeholder="연락처를 입력해주세요." :disabled="!updateState">
        </div>
        <span>{{ checkEmptyTel }}</span>
        <br>
        <div>
            <button class="btn-join" type="button" @click="handelUpdateState()" v-if="updateState">취소</button>
            <button class="btn-join" type="button" @click="handelUpdateState()" v-if="!updateState">수정</button>
            <button class="btn-join" type="button" @click="update" v-if="updateState">완료</button>
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
  userInfo.value = value.data.data;
}).catch(reason => {
    console.log(reason);
});

let update = async () => {
    if(checkEmpty()) {
        try {
            let response = await instance.post('/api/user/updateProc', {
                id: userInfo.value.id,
                name: userInfo.value.name,
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

.contents {
    text-align: center;
    margin-top: 5%;
}

.contents input {
    width: 13%;
    margin-right: 1%;
}

.contents-row button {
    width: 5%;
}

.btn-join {
    width: 10%;
    margin-right: 1%;
}
</style>
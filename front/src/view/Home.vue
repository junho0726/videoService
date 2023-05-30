<template>
    <div>
        <Header @show-sidebar="showSidebar()" @send-keyword="search"/>
        <div class="content-wrap">
            <SideBar v-if="isShowSidebar"/>
            <div class="category-div">
              <div class="category-row">
                <button type="button" @click="findVideoByCategorySeq()" class="category">전체</button>
              </div>
              <div class="category-row" v-for="category in categoryList">
                  <button type="button" @click="findVideoByCategorySeq(category.categorySeq)" :id="'category-' + category.categorySeq" class="category">{{ category.name }}</button>
              </div>
            </div>
            <div class="content" :class="{ 'show-side-bar-content':isShowSidebar }">
              <Video :video-list="videoList" :show-side-bar="isShowSidebar"/>
            </div>
        </div>
    </div>
</template>

<script setup>
import Header from '@/layout/Header.vue';
import SideBar from '@/layout/SideBar.vue';
import { ref } from 'vue';
import axios from "axios";
import Video from "@/components/VideoList.vue";

let isShowSidebar = ref(false);
let categoryList = ref([]);
let videoList = ref([]);

axios.post('/api/category/findAll').then(value => {
    if(value.data.code === '0000') {
        for (let i = 0; i < value.data.data.length; i++) {
            categoryList.value.push(value.data.data[i]);
        }
    } else {
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
    }
}).catch(reason => {
    console.log(reason);
    alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
})

axios.get('/api/video/findAll').then(value => {
    let data = value.data;
    let dataList = data.data;
    videoList.value = [];
    if(data.code === "0000") {
        for(let i = 0; i < dataList.length; i++) {
            videoList.value.push(dataList[i]);
        }
    } else {
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
    }
}).catch(reason => {
    console.log(reason);
    alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
})

function search(q) {
    axios.get('/api/video/findAll?q=' + q).then(value => {
        let data = value.data;
        let dataList = data.data;
        videoList.value = [];
        if(data.code === "0000") {
            for(let i = 0; i < dataList.length; i++) {
                videoList.value.push(dataList[i]);
            }
        } else {
            alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
        }
    }).catch(reason => {
        console.log(reason);
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
    })
}

function findVideoByCategorySeq(categorySeq) {
    if(categorySeq == null) {
      axios.get('/api/video/findAll').then(value => {
        let data = value.data;
        let dataList = data.data;
        videoList.value = [];
        if(data.code === "0000") {
          for(let i = 0; i < dataList.length; i++) {
            videoList.value.push(dataList[i]);
          }
            console.log(videoList.value);
        } else {
          alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
        }
      }).catch(reason => {
        console.log(reason);
        alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
      })
    } else {
      axios.get('/api/video/findAll?categorySeq=' + categorySeq).then(value => {
          let data = value.data;
          let dataList = data.data;
          videoList.value = [];
          if(data.code === "0000") {
              for(let i = 0; i < dataList.length; i++) {
                  videoList.value.push(dataList[i]);
              }

          } else {
              alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
          }
      }).catch(reason => {
          console.log(reason);
          alert('지금은 개발 단계입니다. 서버를 재실행 해주세요.');
      })
    }
}

function showSidebar() {
    isShowSidebar.value = !isShowSidebar.value;
}

</script>

<style scoped>
.category-div {
    display: flex;
    margin-left: 10%;
}

.category-row {
    width: 7%;
    margin: 0 0.5%;
}

.category {
    width: 100%;
    height: 45px;
    background-color: #F2F2F2;
    color: black;
    border: none;
    border-radius: 10px;
}

.content-wrap {
    display: flex;
    flex-direction: column;
}

.content {
    width: 98%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 1%;
}

.show-side-bar-content {
    margin-left: 10%;
    width: 88%;
    display: flex;
    flex-wrap: wrap;
    padding: 1% 1%;
}

</style>
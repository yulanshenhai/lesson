<template>
  <header class="welcome-header">
    <h1 class="title">{{ title }}</h1>
    <h3 class="sub-title">{{ subTitle }}</h3>
  </header>
  <section class="welcome-body">
    <article class="welcome-body-banner">
      <banner/>
    </article>
    <article class="welcome-body-video-list">
      <video-list/>
    </article>
  </section>
  <footer class="welcome-footer">
    <article class="welcome-footer-common-footer">
      <common-footer/>
    </article>
  </footer>
</template>

<script setup>
import {shallowRef} from 'vue';
import Banner from "@/views/welcome/banner";
import VideoList from '@/views/welcome/video-list';
import CommonFooter from '@/components/common-footer';

// data: 首页大标题
const title = shallowRef('绝对精品课 - 在线课堂');

// data: 首页小标题
const subTitle = shallowRef('Welcome To Lesson Project');

</script>

<style lang="scss" scoped>

.welcome-header {

  margin: 20px; // 内边距

  .sub-title {
    color: #807c74;
  }
}

.welcome-body {

  .welcome-body-banner {
    margin: 20px;
  }

  .welcome-body-video-list {
    margin: 0 20px 50px;
  }

}

.welcome-footer {

  .welcome-footer-common-footer {
    margin: 20px 0;
  }

}
</style>
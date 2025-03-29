<template>
  <div class="center">
    <h1>欢迎使用选课系统</h1>
    <h2>请选择功能</h2>
    
    <div class="tabs">
      <!-- 点了登录就会把login赋给activeTab -->
      <button 
        class="tab-button" 
        :class="{ active: activeTab === 'login' }"
        @click="switchTab('login')"  
      >
        登录
      </button>

      <!-- 点了注册就会把register赋给activeTab -->
      <button 
        class="tab-button" 
        :class="{ active: activeTab === 'register' }"
        @click="switchTab('register')" 
      >
        注册
      </button>
    </div>

    <!-- 登录表单，如果activeTab === 'login'则显示 -->
    <div v-if="activeTab === 'login'" class="tab-content">
      <h2>登录</h2>
      <input type="text" placeholder="用户名" v-model="formData.username">
      <input type="password" placeholder="密码" v-model="formData.password">
    </div>

    <!-- 注册表单，如果不是登录那就是注册 -->
    <div v-else class="tab-content">
      <h2>注册</h2>
      <input type="text" placeholder="用户名" v-model="formData.username">
      <input type="email" placeholder="邮箱" v-model="formData.email">
      <input type="password" placeholder="密码" v-model="formData.password">
    </div>

    <div class="tab-content" style="color: black; height: 200px;">
      <label for="radio" style="font-size: 40px;">身份选择</label>
      <div>
        <input type="radio" name="gender" id="student">
        <label for="student">学生</label>
      </div>
      <div>
        <input type="radio" name="gender" id="teacher">
        <label for="teacher">老师</label>
      </div>
    </div>

    <!-- 提交按钮 -->
    <div class="submit-button-container">
      <button @click="submitForm">提交</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'; 
const activeTab = ref('login'); //默认直接登录

// 表单数据
const formData = ref({
  username: '',
  email: '',
  password: ''
});

// 切换标签页
const switchTab = (tab) => {
  activeTab.value = tab;
  // 切换时清空表单数据
  formData.value = {
    username: '',
    email: '',
    password: ''
  };
};

// 提交表单
const submitForm = () => {
  // 验证表单数据
  if (activeTab.value === 'login') {
    // 登录表单验证是否为空
    if (!formData.value.username || !formData.value.password) {
      alert('用户名和密码不能为空！');
      return; // 阻止提交
    }
  } else {
    // 注册表单验证是否为空
    if (!formData.value.username || !formData.value.email || !formData.value.password) {
      alert('用户名、邮箱和密码不能为空！');
      return; // 阻止提交
    }
  }

  // 如果验证通过，弹出提交成功提示
  console.log('提交表单:', formData.value);
  alert('提交成功！');
};
</script>


<style scoped>

.center {
  text-align: center;
  margin: auto;
  width: 1000px;
  background-color: aqua;
  padding: 10px;
  position: fixed;
  top: 0;
  left: 10px;
  right: 10px;
}

h1, h2 {
  color: black;
  text-align: center;
}

.tab-button {
  padding: 10px 20px;
  cursor: pointer;
  border: none;
  background: #f0f0f0;
  margin: 100px;
}

.tab-button.active {
  background: #4CAF50;
  color: white;
}

.tab-content {
  padding: 20px;
  border: 1px solid #ddd;
}

input {
  width: 300px;     
  height: 40px;     
  padding: 8px 12px; 
  font-size: 16px;  
  margin: 5px 0;    
}


.submit-button-container {
  margin-top: 20px; 
}

.submit-button-container button {
  padding: 10px 20px;
  cursor: pointer;
  border: none;
  background: #4CAF50;
  color: white;
  font-size: 16px;
}
</style>
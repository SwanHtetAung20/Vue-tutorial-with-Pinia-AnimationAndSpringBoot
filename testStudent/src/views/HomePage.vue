<script setup lang="ts">
import { storeToRefs } from "pinia";
import { useCounterStore } from "@/stores/counter";
import { ref, onMounted } from "vue";
import UpdateUser from "./UpdateUser.vue";
import router from "@/router";
import SignUp from "./SignUp.vue";

const counter = useCounterStore();
const { userList, isTrue, isAdd } = storeToRefs(counter);
const { findAllUsers, userDelete, changedProfile, isAuthenticated } = counter;
const isPya = ref(false);

const selectedUser = ref({});
onMounted(async () => {
  if (isAuthenticated) {
    try {
      const res = await findAllUsers();
      if (!res.userList) {
        alert(res.error);
        return;
      }
      console.log(res.userList);
    } catch (error) {
      alert("something wrong Please try again!");
    }
  } else {
    router.push("/");
  }
});

const deleteUser = async (id: number) => {
  var confirm = window.confirm(
    "Are you sure want to delete this suer with id no" + id
  );
  if (confirm) {
    try {
      const res = await userDelete(id);
      if (!res.message) {
        alert(res.error);
        return;
      }
      alert(res.message);
    } catch (error) {
      alert(error);
    }
  }
};

const handleFileChange = async (event: Event, id: number) => {
  const fileInput = event.target as HTMLInputElement;
  const file = fileInput.files ? fileInput.files[0] : null;
  if (file) {
    const res = await changedProfile(id, file);
    if (!res.message) {
      alert(res.error);
      return;
    }
    alert(res.message);
  } else {
    alert("Photo Cannot be empty");
  }
};

const changeProfile = (id: number) => {
  const fileInput = document.getElementById(
    `fileInput-${id}`
  ) as HTMLInputElement;
  if (fileInput) {
    fileInput.click();
  }
};

const isShow = (user: Object) => {
  selectedUser.value = user;
  isTrue.value = !isTrue.value;
};
</script>

<template>
  <div>
    <h1>User View</h1>
    <hr />
    <table class="user-table">
      <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Email</th>
          <th>Phone Number</th>
          <th>Gender</th>
          <th>Profile</th>
          <th>Update</th>
          <th>Delete</th>
          <th>Change Profile</th>
        </tr>
      </thead>
      <transition name="switch" mode="out-in">
        <transition-group v-if="!userList.length" tag="tbody">
          <tr>
            <td colspan="9" style="text-align: center">No users available.</td>
          </tr>
        </transition-group>
        <transition-group v-else tag="tbody" name="fade" appear>
          <tr v-for="user in userList" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.name ? user.name : "UNDEFINED" }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.phoneNumber ? user.phoneNumber : "UNDEFINED" }}</td>
            <td>{{ user.gender }}</td>
            <td>
              <div v-if="user.photo">
                <img
                  :src="'data:image/**;base64,' + user.photo"
                  alt="Profile Image"
                  :style="{ height: '100px', width: '100px' }"
                />
              </div>
              <div v-else>UNDEFINED</div>
            </td>
            <td><button @click="isShow(user)">Update</button></td>
            <td><button @click="deleteUser(user.id)">Delete</button></td>
            <td @click="changeProfile(user.id)">
              <button>Click to Change</button>
            </td>
            <input
              type="file"
              :id="`fileInput-${user.id}`"
              style="display: none"
              @change="handleFileChange($event, user.id)"
            />
          </tr>
        </transition-group>
      </transition>
    </table>

    <div v-if="isTrue">
      <UpdateUser :user="selectedUser" />
    </div>

    <div class="container-div">
      <transition name="kyiMal">
        <div v-if="isPya">
          <h1 class="box-test">Test Adnimation And Shake Test</h1>
        </div>
      </transition>
      <button @click="isPya = !isPya">Show Or Not</button>
    </div>

    <div class="container-div">
      <div v-show="isAdd">
        <SignUp />
      </div>

      <button @click="isAdd = !isAdd">Add Users</button>
    </div>
  </div>
</template>

<style scoped>
.container-div {
  text-align: center;
}

.box-test {
  border: 2px solid black;
  padding: 10px;
  display: inline-block;
  border-radius: 10px;
}

/* .kyiMal-enter-from {
  opacity: 0;
  transform: translateY(80px);
}
.kyiMal-enter-to {
  opacity: 1;
  transform: translateY(0);
} */
.kyiMal-enter-active {
  /* transition: all 0.5s ease; */
  animation: firstTest 0.5s ease;
}

.kyiMal-leave-from {
  opacity: 1;
  transform: translateY(0);
}
.kyiMal-leave-to {
  opacity: 0;
  transform: translateY(-80px);
}
.kyiMal-leave-active {
  transition: all 0.5s ease;
}

@keyframes firstTest {
  0% {
    transform: translateY(80px);
    opacity: 0;
  }
  50% {
    transform: translateY(0);
    opacity: 1;
  }
  60% {
    transform: translateX(8px);
  }
  70% {
    transform: translateX(-8px);
  }
  80% {
    transform: translateX(4px);
  }
  90% {
    transform: translateX(-4px);
  }
  100% {
    transform: translateX(0);
  }
}
/* for table animation start */
.fade-enter-from {
  opacity: 0;
  transform: scale(0.6);
}
.fade-enter-to {
  opacity: 1;
  transform: scale(1);
}
.fade-enter-active {
  transition: all 0.5s ease;
}

.fade-leave-from {
  opacity: 1;
  transform: scale(1);
}
.fade-leave-to {
  opacity: 0;
  transform: scale(0.6);
}
.fade-leave-active {
  transition: all 0.5s ease;
  position: absolute;
}

.fade-move {
  transition: all 0.5s ease;
}
/* for table animation end */

/* for table conditon animation start */
.switch-enter-from,
.switch-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

.switch-enter-to, /* default! you can delete it */
.switch-leave-from {
  opacity: 1;
  transform: translateY(0);
}

.switch-enter-active,
.switch-leave-active {
  transition: all 0.5s ease;
}

/* for table conditonn animation start */
</style>

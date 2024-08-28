import { ref, computed, reactive } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore("counter", () => {
  let user = reactive({});
  let userList: any = ref([]);
  let isTrue = ref(false);
  const isAdd = ref(false);

  enum GENDER {
    MALE,
    FEMALE,
    UNDEFINED,
  }

  interface SignUpData {
    email: string;
    password: string;
    gender: GENDER;
  }

  const isAuthenticated = computed((): boolean => {
    const isExisted = localStorage.getItem("user");
    return !!isExisted;
  });

  const userLogin = async (email: string, password: string) => {
    try {
      const res = await axios.post(`/api/login`, { email, password });
      Object.assign(user, res.data.user);
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const userSignUp = async (obj: SignUpData) => {
    try {
      const res = await axios.post(`/api/sign-up`, obj);
      Object.assign(user, res.data.user);
      var loginUser = localStorage.getItem("user");
      if (loginUser) {
        userList.value.push(res.data.user);
      }
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const findAllUsers = async () => {
    try {
      const res = await axios.get(`/api/get-all`);
      userList.value = res.data.userList;
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const userDelete = async (id: number) => {
    try {
      const res = await axios.delete(`/api/${id}`);
      userList.value = userList.value.filter((user) => user.id !== id);
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const userUpdate = async (obj: Object) => {
    try {
      const res = await axios.put(`/api/update`, obj);
      const index = userList.value.findIndex((user) => user.id === obj.id);
      if (index !== -1) {
        userList.value[index] = res.data.user;
        isTrue.value = false;
      }
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const changedProfile = async (id: number, file: File) => {
    try {
      const formData = new FormData();
      formData.append("id", id.toString());
      formData.append("file", file);

      const res = await axios.put(`/api/change-profile`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      const index = userList.value.findIndex((user) => user.id === id);
      if (index !== -1) {
        userList.value[index] = res.data.user;
        isTrue.value = false;
      }
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    user,
    userLogin,
    GENDER,
    userSignUp,
    findAllUsers,
    userDelete,
    userUpdate,
    userList,
    isTrue,
    changedProfile,
    isAuthenticated,
    isAdd,
  };
});

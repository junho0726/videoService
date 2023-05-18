import {createStore} from 'vuex';
import user from '@/store/modules/user';
import channel from "@/store/modules/channel";

export default createStore({
    modules: {
        user,
        channel
    }
})


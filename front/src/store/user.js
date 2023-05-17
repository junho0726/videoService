let state = {
    token: null
}

let getters = {
    getToken: (state) => {
        return state.token;
    }
}

let actions = {
    async setToken({ commit }, token) {
        console.log(token);
        return commit('setToken', token);
    }
}

let mutations = {
    setToken(state, token) {
        console.log(token);
        state.token = token;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
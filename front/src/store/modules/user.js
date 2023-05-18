let state = {
    token: null,
    userSeq: 0
}

let getters = {
    getToken: (state) => {
        return state.token;
    },
    getUserSeq: (state) => {
        return state.userSeq;
    },
}

let actions = {
    async setToken({ commit }, token) {
        return commit('setToken', token);
    },
    async setUserSeq({ commit }, token) {
        return commit('setUserSeq', token);
    },
}

let mutations = {
    setToken(state, token) {
        state.token = token;
    },
    setUserSeq(state, token) {
        state.userSeq = token;
    },
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
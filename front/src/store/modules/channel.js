let state = {
    channelSeq: null
}

let getters = {
    getChannelSeq: (state) => {
        return state.channelSeq;
    }
}

let actions = {
    async setChannelSeq({commit}, channelSeq) {
        return commit('setChannelSeq', channelSeq);
    }
}

let mutations = {
    setChannelSeq(state, channelSeq) {
        state.channelSeq = channelSeq;
    }
}

export default {
    namespace: true,
    state,
    getters,
    actions,
    mutations
}
module.exports = function override(config, env) {
    // do stuff with the webpack config...
    config.externals = {
        'config': JSON.stringify({
            'apiUrl': 'http://localhost:3000'
        })
    };
    return config;
};
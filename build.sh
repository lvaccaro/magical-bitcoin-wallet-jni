CC="aarch64-linux-android21-clang" cargo build --target=aarch64-linux-android

cp target/aarch64-linux-android/debug/libmagical_bitcoin_wallet_jni.so library/src/main/jniLibs/arm64-v8a


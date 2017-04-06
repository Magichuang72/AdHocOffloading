#include <jni.h>
#include "Pso.h"
#include <stdio.h>
#include <iostream>
using namespace std;
jintArray copyArray(JNIEnv *env, jobject obj,jintArray x);
JNIEXPORT jintArray JNICALL Java_Pso_algorithmStart
  (JNIEnv *env, jobject obj, jintArray a, jintArray b, jintArray c, jintArray d) {
    jintArray task = copyArray(env,obj,a);
    jintArray speed = copyArray(env,obj,b);
    jintArray cost = copyArray(env,obj,c);
    jintArray want = copyArray(env,obj,d);
    jint size = env->GetArrayLength(b);
    jintArray result = env->NewIntArray(size);
    return result;
}
jintArray copyArray(JNIEnv *env, jobject obj,jintArray x) {
    jint len = env->GetArrayLength(x);
    jint *body = env->GetIntArrayElements(x, 0);
    jint i = 0;
    jintArray num = env->NewIntArray(len);
    for (; i < len; i++) {
        num[i] = body[i];
     }

    return num;
}
void start() {
int[][] x = new int[size][task];
int[][] v = new int[node][task];
int fitness = fit(x);
 if(fitness < pbestfit) {
 pbestfit = fitness;
 pbest = x
if(fitness < gbestfit) {
gbestfit = fitness;
gbest = x;
}
 }




}
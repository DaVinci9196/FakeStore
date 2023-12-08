package com.huawei.signature.diff;

interface ISignatureService {
    String[] querySignature(String packageName, boolean isDiffSignatureRequired);
}
package com.quick.recording.auth.service.security.enumeration;

public enum AuthProvider {
  YANDEX("yandex"),
  VK("vk");

  private final String name;
  AuthProvider(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

package com.yoong.myissue.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

abstract class QueryDslSupport {

    @PersistenceContext
    protected lateinit var em: EntityManager

    protected val queryFactory: JPAQueryFactory
        get() {
            return JPAQueryFactory(em)
        }
}
/**
 * Created by zzt on 17/6/21.
 *
 * create index dynamically, because data in this package is all time-based data, which should not
 * be put in a single index, but put in multiple index using time to split. E.g. chat_2017_6,
 * chat_2017_7, or even make scale smaller later, chat_2017_8_1
 */
package cn.superid.search.impl.query.time;
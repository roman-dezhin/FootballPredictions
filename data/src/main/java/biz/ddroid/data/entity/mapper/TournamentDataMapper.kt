package biz.ddroid.data.entity.mapper

import biz.ddroid.data.entity.TournamentResultEntity
import biz.ddroid.data.entity.TournamentResultRowEntity
import biz.ddroid.domain.data.TournamentData
import biz.ddroid.domain.data.TournamentResultData
import biz.ddroid.domain.data.TournamentResultRowData

class TournamentDataMapper : Mapper<List<TournamentResultEntity>, List<TournamentResultData>> {
    override fun map(source: List<TournamentResultEntity>): List<TournamentResultData> {
        return source.map { map(it) }
    }

    private fun map(source: TournamentResultEntity): TournamentResultData {
        return TournamentResultData(
            TournamentData(source.id, source.name, isTournamentFinished(source.isFinished)),
            getResults(source.tournamentResultRowEntity)
        )
    }

    private fun getResults(tournamentResultRowEntity: List<TournamentResultRowEntity>): List<TournamentResultRowData> {
        return tournamentResultRowEntity.map {
            TournamentResultRowData(
                it.userName,
                it.points,
                it.predictions,
                it.scores,
                it.results,
                isUserWinner(it.isWinner)
            )
        }
    }

    private fun isTournamentFinished(value: Int): Boolean {
        return value != 0
    }

    private fun isUserWinner(value: Int): Boolean {
        return value != 0
    }

}
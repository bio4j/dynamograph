package com.bio4j.dynamograph.dao.go

import com.bio4j.dynamograph.model.go.nodes.GOTermImpl

trait GOTermDAO {
  def get(id : String) : GOTermImpl

}

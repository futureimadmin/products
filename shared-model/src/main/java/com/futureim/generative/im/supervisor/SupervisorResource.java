package com.futureim.generative.im.supervisor;

import com.futureim.generative.im.mcp.MCPResource;

import java.util.Map;

public interface SupervisorResource extends MCPResource {
    void monitorProject(Map<String, Object> parameters);
    void coordinateAgents(Map<String, Object> parameters);
}
